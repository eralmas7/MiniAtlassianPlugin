package com.sample.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.atlassian.jira.rest.client.RestClientException;
import com.sample.project.exception.InvalidAddressException;
import com.sample.project.model.ExceptionData;
import com.sample.project.model.JiraViolationResponse;
import com.sample.project.service.AuditService;

/**
 * Spring's rest controller which will expose rest api's to get web page details.
 */
@RestController
@RequestMapping("/rest/agile")
public class JiraAuditController {

  private final AuditService auditService;

  @Autowired
  public JiraAuditController(final AuditService auditService) {
    this.auditService = auditService;
  }

  /**
   * Evaluate and get all the jira details
   * @param sprintId
   * @param projectName
   * @param miscQuery
   * @return
   */
  @RequestMapping(value = "/1.0", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE })
  public JiraViolationResponse getWebPageDetails(final @RequestParam("sprintId") String sprintId,
      final @RequestParam("projectName") String projectName,
      final @RequestParam("miscQuery") String miscQuery) {
    return auditService.getJiraDetails(sprintId, projectName, miscQuery);
  }

  /**
   * Handle all type of validation exceptions.
   * @param invalidAddressException
   * @return
   */
  @ResponseBody
  @ExceptionHandler(InvalidAddressException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ExceptionData handleException(final InvalidAddressException invalidAddressException) {
    return new ExceptionData(invalidAddressException.getMessage(), HttpStatus.BAD_REQUEST.value(),
        invalidAddressException.toString());
  }

  /**
   * Handle any rest exception from jira service.
   * @param exception
   * @return
   */
  @ResponseBody
  @ExceptionHandler(RestClientException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ExceptionData handleRestException(final Exception exception) {
    return new ExceptionData(exception.getMessage(), HttpStatus.BAD_REQUEST.value(),
        exception.toString());
  }

  /**
   * Handle any other exception.
   * @param exception
   * @return
   */
  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionData handleException(final Exception exception) {
    return new ExceptionData(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
        exception.toString());
  }
}
