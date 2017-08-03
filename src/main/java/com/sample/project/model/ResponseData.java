package com.sample.project.model;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResponseData {

  private String issueId;
  private String problems;
  private String issueName;
  private String issueType;
  private int warningCount;
  private Map<String, Object> specificFields;
  private String assignee;
  private String status;
  private String description;
  private String reporter;
  private String summary;
  private String peerReviewer;

  public ResponseData() {
  }

  public String getIssueId() {
    return issueId;
  }

  public void setIssueId(final String issueId) {
    this.issueId = issueId;
  }

  public String getProblems() {
    return problems;
  }

  public void setProblems(final String problems) {
    this.problems = problems;
  }

  public String getIssueName() {
    return issueName;
  }

  public void setIssueName(final String issueName) {
    this.issueName = issueName;
  }

  public String getIssueType() {
    return issueType;
  }

  public void setIssueType(final String issueType) {
    this.issueType = issueType;
  }

  public Map<String, Object> getSpecificFields() {
    return specificFields;
  }

  public void setSpecificFields(final Map<String, Object> specificFields) {
    this.specificFields = specificFields;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(final String assignee) {
    this.assignee = assignee;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getReporter() {
    return reporter;
  }

  public void setReporter(final String reporter) {
    this.reporter = reporter;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(final String summary) {
    this.summary = summary;
  }

  public String getPeerReviewer() {
    return peerReviewer;
  }

  public void setPeerReviewer(final String peerReviewer) {
    this.peerReviewer = peerReviewer;
  }

  public int getWarningCount() {
    return warningCount;
  }

  public void setWarningCount(int warningCount) {
    this.warningCount = warningCount;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
