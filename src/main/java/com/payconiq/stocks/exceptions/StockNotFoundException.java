package com.payconiq.stocks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class when stock is not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public StockNotFoundException(String excMessage) {
    super(excMessage);
  }

  public StockNotFoundException() {
    super();
  }
}
