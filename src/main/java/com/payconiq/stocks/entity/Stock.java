package com.payconiq.stocks.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Entity class for stock.
 */
@Entity
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long stockId;

  @Column(nullable = false)
  @Size(min = 3, max = 30, message = "Size should be between 3 and 30")
  @NotEmpty(message = "Enter some valid stock name")
  private String stockName;

  @Column(nullable = false)
  @Min(0)
  private double stockPrice;

  @Column(nullable = false)
  private LocalDateTime stockTimestamp;

  public Stock() {}

  public Stock(long stockId, String stockName, double stockPrice, LocalDateTime stockTimestamp) {
    super();
    this.stockId = stockId;
    this.stockName = stockName;
    this.stockPrice = stockPrice;
    this.stockTimestamp = stockTimestamp;
  }

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
