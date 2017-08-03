package com.sample.project.service;

/**
 * Interface to create query. 
 */
public interface QueryService {

  /**
   * For query using parameters passed as input.
   * @param sprintId
   * @param projectName
   * @param miscQuery
   * @return
   */
  public String formQuery(final String sprintId, final String projectName, final String miscQuery);
}
