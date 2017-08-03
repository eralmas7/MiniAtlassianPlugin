package com.sample.project.service;

import com.sample.project.model.JiraViolationResponse;

/**
 * A service to get jira details.
 * 
 */
public interface AuditService {

  /**
   * Get the jira details by querying jira dash board via rest.
   * @param sprintId
   * @param projectName
   * @param miscQuery
   * @return
   */
  public JiraViolationResponse getJiraDetails(final String sprintId, final String projectName,
      final String miscQuery);

}
