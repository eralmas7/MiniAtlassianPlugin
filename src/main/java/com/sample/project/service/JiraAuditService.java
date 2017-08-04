package com.sample.project.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.IssueRestClient.Expandos;
import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Field;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.google.common.util.concurrent.FutureCallback;
import com.sample.project.model.JiraViolationResponse;
import com.sample.project.model.ProblemDetails;
import com.sample.project.utils.Constants;

@Service
public class JiraAuditService implements AuditService {

  private static String[] FIELDS_TO_EXTRACT;
  private static String[] REQUIRED_JIRA_LABELS;
  private static final List<Expandos> epandos = Arrays.asList(Expandos.values());
  private static final Logger logger = LoggerFactory.getLogger(JiraAuditService.class);
  private final JiraRestClient jiraRestClient;
  private final QueryService queryService;
  private final Converter converter;
  private static String jiraUrl;

  private static class CallBack implements FutureCallback<Issue> {

    private final Map<String, ProblemDetails> jiraProblemDetailsMap;

    public CallBack(final Map<String, ProblemDetails> jiraProblemDetailsMap) {
      this.jiraProblemDetailsMap = jiraProblemDetailsMap;
    }

    @Override
    public void onFailure(final Throwable throwable) {
      logger.warn("onFailure: {}", throwable);// fine no-op on failure to get data of one jira.
    }

    @Override
    public void onSuccess(final Issue issue) {
      if (!"Epic".equals(issue.getIssueType().getName())) {
        validateFields(issue.getKey(), issue, jiraProblemDetailsMap);
        validateLabels(issue.getKey(), issue, jiraProblemDetailsMap);
      }
    }

    private void validateLabels(final String jiraId, final Issue issue,
        final Map<String, ProblemDetails> jiraProblemDetailsMap) {
      for (String label : REQUIRED_JIRA_LABELS) {
        if (!issue.getLabels().contains(label)) {
          ProblemDetails problemDetails = jiraProblemDetailsMap.compute(jiraId,
              (k, v) -> v == null ? new ProblemDetails() : v);
          problemDetails.addViolation(String.format("Doesn't have %s label.", label));
          problemDetails.putFieldNameValue(Constants.IS_JIRA_LABEL_VALID, Boolean.FALSE);
        }
      }
    }

    private void validateFields(final String jiraId, final Issue issue,
        final Map<String, ProblemDetails> jiraProblemDetailsMap) {
      final ProblemDetails problemDetails = jiraProblemDetailsMap.compute(jiraId,
          (k, v) -> v == null ? new ProblemDetails() : v);
      fillInJiraFields(issue, issue.getFields(), problemDetails, jiraId);
      for (String string : FIELDS_TO_EXTRACT) {
        if (!problemDetails.containsKeyAndValue(string)) {
          problemDetails.addViolation(String.format("%s field can't be empty", string));
          problemDetails.putFieldNameValue(Constants.IS_JIRA_STATUS_VALID, Boolean.FALSE);
        }
      }

      if (problemDetails.getValue(Constants.JIRA_STATUS) != "Done") {
        problemDetails.addViolation(String.format("%s is the state while expected Done state",
            problemDetails.getValue(Constants.JIRA_STATUS)));
      }
    }

    private String extractName(final Object object) {
      try {
        return object != null && !StringUtils.isEmpty(object.toString())
            ? ((JSONObject) object).getString("displayName") : null;
      } catch (JSONException e) {
        return null;
      }
    }

    private void fillInJiraFields(final Issue issue, final Iterable<Field> fieldIterable,
        final ProblemDetails problemDetails, final String jiraId) {
      for (Field field : fieldIterable)
        problemDetails.putFieldNameValue(field.getName(), field.getValue());
      problemDetails.putFieldNameValue(Constants.JIRA_DESCRIPTION, issue.getDescription());
      problemDetails.putFieldNameValue(Constants.JIRA_URL, jiraUrl + jiraId);
      problemDetails.putFieldNameValue(Constants.ASSIGNEE,
          issue.getAssignee() == null ? StringUtils.EMPTY : issue.getAssignee().getDisplayName());
      problemDetails.putFieldNameValue(Constants.JIRA_SUMMARY, issue.getSummary());
      problemDetails.putFieldNameValue(Constants.JIRA_TYPE, issue.getIssueType().getName());
      problemDetails.putFieldNameValue(Constants.JIRA_STATUS, issue.getStatus().getName());
      problemDetails.putFieldNameValue(Constants.JIRA_REVIEWER,
          extractName(problemDetails.getAndRemoveValue(Constants.JIRA_REVIEWER)));
      problemDetails.putFieldNameValue(Constants.JIRA_REPORTER,
          issue.getReporter().getDisplayName());
      problemDetails.putFieldNameValue(Constants.JIRA_LABEL,
          StringUtils.join(issue.getLabels(), ","));
    }

  }

  @Autowired
  public JiraAuditService(final JiraRestClient jiraRestClient, final QueryService queryService,
      final @Qualifier("jiraFields") String[] fieldsFromJira,
      final @Qualifier("labelFields") String[] mandatoryLabels, final Converter converter,
      final String jiraBrowseUrl) {
    this.jiraRestClient = jiraRestClient;
    this.queryService = queryService;
    FIELDS_TO_EXTRACT = fieldsFromJira;
    REQUIRED_JIRA_LABELS = mandatoryLabels;
    this.converter = converter;
    jiraUrl = jiraBrowseUrl;
  }

  @Override
  public JiraViolationResponse getJiraDetails(final String sprintId, final String projectName,
      final String miscQuery) {
    return jiraRestClient.getSearchClient()
        .searchJql(queryService.formQuery(sprintId, projectName, miscQuery))
        .map(searchResult -> generateViolations(searchResult)).claim();
  }

  private JiraViolationResponse generateViolations(final SearchResult searchResult) {
    final Map<String, ProblemDetails> jiraProblemDetailsMap = new ConcurrentHashMap<>();
    for (BasicIssue issue : searchResult.getIssues()) {
      Issue issueResult = jiraRestClient.getIssueClient().getIssue(issue.getKey(), epandos).claim();
      new CallBack(jiraProblemDetailsMap).onSuccess(issueResult);
    }
    return this.converter.convert(jiraProblemDetailsMap, FIELDS_TO_EXTRACT);
  }

}
