package com.sample.project.exception;

public class InvalidAddressException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a InvalidAddressException with the specified detail message.
   * 
   * @param message the detail message.
   */
  public InvalidAddressException(final String message) {
    super(message);
  }

  /**
   * Constructs a InvalidAddressException with the specified detail message and cause of the same.
   * 
   * @param message - detail exception message
   * @param throwable - cause of exception
   */
  public InvalidAddressException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
