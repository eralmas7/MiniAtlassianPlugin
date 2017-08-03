package com.sample.project.service;

import java.util.Map;

import com.sample.project.model.JiraViolationResponse;
import com.sample.project.model.ProblemDetails;

/**
 * Converts map to response needed.
 * @author almass
 *
 */
public interface Converter {

  /**
   * Convert raw jira data to output that needs to be passed to UI.
   * @param jiraProblemDetailsMap
   * @param jiraFields
   * @return
   */
  public JiraViolationResponse convert(final Map<String, ProblemDetails> jiraProblemDetailsMap,
      final String[] jiraFields);
}
