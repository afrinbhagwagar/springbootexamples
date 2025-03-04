package com.payconiq.stocks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class when inputs are not correct.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {


  private static final long serialVersionUID = 1L;

  public InvalidInputException(String excMessage) {
    super(excMessage);
  }

  public InvalidInputException() {
    super();
  }
}
