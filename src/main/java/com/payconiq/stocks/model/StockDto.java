package com.payconiq.stocks.model;

import java.time.LocalDateTime;

/**
 * Stock DTO class with getters / setters.
 */
public class StockDto {

  private long stockId;
  private String stockName;
  private double stockPrice;
  private LocalDateTime stockTimestamp;

  public StockDto(long stockId, String stockName, double stockPrice, LocalDateTime stockTimestamp) {
    super();
    this.stockId = stockId;
    this.stockName = stockName;
    this.stockPrice = stockPrice;
    this.stockTimestamp = stockTimestamp;
  }

  public StockDto() {}

  public long getStockId() {
    return stockId;
  }

  public void setStockId(long stockId) {
    this.stockId = stockId;
  }

  public String getStockName() {
    return stockName;
  }

  public void setStockName(String stockName) {
    this.stockName = stockName;
  }

  public double getStockPrice() {
    return stockPrice;
  }

  public void setStockPrice(double stockPrice) {
    this.stockPrice = stockPrice;
  }

  public LocalDateTime getStockTimestamp() {
    return stockTimestamp;
  }

  public void setStockTimestamp(LocalDateTime stockTimestamp) {
    this.stockTimestamp = stockTimestamp;
  }

}
