package com.sample.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point into the application. 
 */
@SpringBootApplication
public class Bootstrap {

  public static void main(final String[] commandLineArgs) {
    SpringApplication.run(Bootstrap.class, commandLineArgs);
  }
}
