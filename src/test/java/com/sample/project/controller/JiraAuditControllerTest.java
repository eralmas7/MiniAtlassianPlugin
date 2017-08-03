package com.sample.project.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.project.model.ExceptionData;
import com.sample.project.model.JiraViolationResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JiraAuditControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void givenValidSpring_whenJiraViolationRestIsCalled_ReturnsOK() throws Exception {
    // Arrange
    final Map<String, String> parameters = new HashMap<>();
    parameters.put("sprintId", "1");
    // Act
    final JiraViolationResponse responseData = restTemplate.getForObject(
        "/rest/agile/1.0?sprintId={sprintId}&projectName=''&miscQuery=''",
        JiraViolationResponse.class, parameters);
    // Assert
    assertThat(responseData.getTotalIssuesUnderViolation()).isEqualTo(0);
  }

  @Test
  public void givenInValidSprint_whenJiraViolationRestIsCalled_ReturnsException() throws Exception {
    // Arrange
    final Map<String, String> parameters = new HashMap<>();
    parameters.put("sprintId", "102020");
    // Act
    final ExceptionData exceptionData = restTemplate.getForObject(
        "/rest/agile/1.0?sprintId={sprintId}&projectName=''&miscQuery=''", ExceptionData.class,
        parameters);
    // Assert
    assertThat(exceptionData.getErrorCode()).isEqualTo(500);
  }

  @Test
  public void givenEmptyUrl_whenCrawlApiIsCalled_ReturnsException() throws Exception {
    // Arrange
    final Map<String, String> parameters = new HashMap<>();
    parameters.put("sprintId", "");
    // Act
    final ExceptionData exceptionData = restTemplate.getForObject(
        "/rest/agile/1.0?sprintId={sprintId}&projectName=''&miscQuery=''", ExceptionData.class,
        parameters);
    // Assert
    assertThat(exceptionData.getErrorCode()).isEqualTo(500);
  }

  @Test
  public void givenInvalidProject_whenCrawlApiIsCalled_ReturnsException() throws Exception {
    // Arrange
    final Map<String, String> parameters = new HashMap<>();
    parameters.put("sprintId", "1");
    parameters.put("projectName", "NoIdea");
    // Act
    final ExceptionData exceptionData = restTemplate.getForObject(
        "/rest/agile/1.0?sprintId={sprintId}&projectName={projectName}&miscQuery=''",
        ExceptionData.class, parameters);
    // Assert
    assertThat(exceptionData.getErrorCode()).isEqualTo(500);
  }

}
