package com.sample.project.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ExceptionData {

  private String message;
  private int errorCode;
  private String stackTrace;

  public ExceptionData() {
  }

  public ExceptionData(final String message, final int errorCode, final String stackTrace) {
    this.message = message;
    this.errorCode = errorCode;
    this.stackTrace = stackTrace;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(final int errorCode) {
    this.errorCode = errorCode;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(final String stackTrace) {
    this.stackTrace = stackTrace;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
