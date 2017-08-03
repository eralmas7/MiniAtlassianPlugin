package com.sample.project.model;

import java.util.List;

public class JiraViolationResponse {

  private List<ResponseData> responseDataList;
  private int totalIssuesUnderViolation;

  public JiraViolationResponse() {
  }

  public JiraViolationResponse(final List<ResponseData> responseDataList,
      final int totalIssuesUnderViolation) {
    this.responseDataList = responseDataList;
    this.totalIssuesUnderViolation = totalIssuesUnderViolation;
  }

  public List<ResponseData> getResponseDataList() {
    return responseDataList;
  }

  public int getTotalIssuesUnderViolation() {
    return totalIssuesUnderViolation;
  }

  public void setResponseDataList(List<ResponseData> responseDataList) {
    this.responseDataList = responseDataList;
  }

  public void setTotalIssuesUnderViolation(int totalIssuesUnderViolation) {
    this.totalIssuesUnderViolation = totalIssuesUnderViolation;
  }

}
