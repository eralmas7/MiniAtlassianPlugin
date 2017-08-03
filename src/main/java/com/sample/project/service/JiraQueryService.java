package com.sample.project.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class JiraQueryService implements QueryService {

  @Override
  public String formQuery(final String sprintId, final String projectName, final String miscQuery) {
    final StringBuilder finalQuery = new StringBuilder();
    if (!StringUtils.isEmpty(sprintId)) {
      finalQuery.append("Sprint = " + sprintId);
    }
    if (!StringUtils.isEmpty(projectName)) {
      finalQuery.append(" AND Project = " + projectName);
    }

    if (!StringUtils.isEmpty(miscQuery)) {
      finalQuery.append(" AND " + miscQuery);
    }
    return finalQuery.toString();
  }

}
