package com.sample.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sample.project.model.JiraViolationResponse;
import com.sample.project.model.ProblemDetails;
import com.sample.project.model.ResponseData;
import com.sample.project.utils.Constants;

@Service
public class JiraResponseConverter implements Converter {

  @Override
  public JiraViolationResponse convert(final Map<String, ProblemDetails> jiraProblemDetailsMap,
      final String[] jiraFields) {
    final List<ResponseData> responses = new ArrayList<>(jiraProblemDetailsMap.size());

    int violationCount = 0;

    for (Map.Entry<String, ProblemDetails> entry : jiraProblemDetailsMap.entrySet()) {
      final ResponseData responseData = new ResponseData();
      final ProblemDetails problemDetails = entry.getValue();
      responseData.setWarningCount(problemDetails.getViolationCount());
      responseData.setAssignee(problemDetails.getAndRemoveValue(Constants.ASSIGNEE).toString());
      responseData
          .setDescription(problemDetails.getAndRemoveValue(Constants.JIRA_DESCRIPTION).toString());
      responseData.setIssueId(entry.getKey().toString());
      responseData.setSummary(problemDetails.getAndRemoveValue(Constants.JIRA_SUMMARY).toString());
      responseData.setIssueType(problemDetails.getAndRemoveValue(Constants.JIRA_TYPE).toString());
      responseData
          .setProblems(problemDetails.getAllViolations(System.getProperty("line.separator")));
      responseData
          .setReporter(problemDetails.getAndRemoveValue(Constants.JIRA_REPORTER).toString());
      responseData
          .setPeerReviewer(problemDetails.getAndRemoveValue(Constants.JIRA_REVIEWER).toString());
      responseData.setStatus(problemDetails.getAndRemoveValue(Constants.JIRA_STATUS).toString());
      problemDetails.retainValues(jiraFields);
      responseData.setSpecificFields(problemDetails.getAllFields());
      responses.add(responseData);
      if (problemDetails.getViolationCount() > 0) {
        violationCount++;
      }
    }
    return new JiraViolationResponse(responses, violationCount);
  }

}
