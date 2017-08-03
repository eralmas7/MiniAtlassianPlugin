package com.sample.project.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProblemDetails {

  private final List<String> violations;
  private final Map<String, Object> jiraFieldNameValueMap;

  public ProblemDetails() {
    violations = new ArrayList<>();
    jiraFieldNameValueMap = new HashMap<>();
  }

  public Object getAndRemoveValue(final String key) {
    final Object object = jiraFieldNameValueMap.remove(key);
    return object == null ? "" : object;
  }

  public Object getValue(final String key) {
    return jiraFieldNameValueMap.getOrDefault(key, "");
  }

  public Map<String, Object> getAllFields() {
    return jiraFieldNameValueMap;
  }

  public void addViolation(final String violation) {
    violations.add(violation);
  }

  public void putFieldNameValue(final String fieldName, final Object fieldValue) {
    jiraFieldNameValueMap.put(fieldName, fieldValue);
  }

  public boolean containsKeyAndValue(final String fieldName) {
    return jiraFieldNameValueMap.get(fieldName) != null;
  }

  public void retainValues(final String[] array) {
    jiraFieldNameValueMap.keySet().retainAll(Arrays.asList(array));
  }

  public String getAllViolations(final String seperator) {
    return StringUtils.join(violations, seperator);
  }

  public int getViolationCount() {
    return this.violations.size();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
