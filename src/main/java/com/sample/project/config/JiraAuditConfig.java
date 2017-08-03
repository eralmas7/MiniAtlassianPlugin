package com.sample.project.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.JiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.sample.project.exception.InvalidAddressException;

@Configuration
public class JiraAuditConfig {

  private final Environment environment;

  @Autowired
  public JiraAuditConfig(final Environment environment) {
    this.environment = environment;
  }

  @Bean
  public JiraRestClient getJiraRestClient() {
    try {
      final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
      final URI uri = new URI(
          environment.getProperty("com.sample.project.address", "http://codegist.atlassian.net"));
      return factory.createWithBasicHttpAuthentication(uri,
          environment.getProperty("com.sample.project.user"),
          environment.getProperty("com.sample.project.password"));
    } catch (Exception e) {
      throw new InvalidAddressException("Got an exception while creating URI ", e);
    }
  }

  @Bean
  public String jiraUrl() {
    return environment.getProperty("com.sample.project.address", "http://codegist.atlassian.net")
        + "/browse/";
  }

  @Bean
  @Qualifier("jiraFields")
  public String[] getFieldsFromJira() {
    return environment.getProperty("com.sample.project.mandatory.fields").split("\\s*,\\s*");
  }

  @Bean
  @Qualifier("labelFields")
  public String[] getMandatoryLabelsFromJira() {
    return environment.getProperty("com.sample.project.mandatory.labels").split("\\s*,\\s*");
  }
}
