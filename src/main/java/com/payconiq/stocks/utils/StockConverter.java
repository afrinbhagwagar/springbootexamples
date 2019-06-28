package com.payconiq.stocks.utils;

import java.time.LocalDateTime;

import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.model.StockDto;

/**
 * Converter class.
 */
public class StockConverter {

  /**
   * Method responsible for DTO to Entity conversion.
   * 
   * @param stockDto stockDTO as an input.
   * @return Stock as return value.
   */
  public static Stock dtoToEntity(StockDto stockDto) {
    return new Stock(stockDto.getStockId(), stockDto.getStockName(), stockDto.getStockPrice(), LocalDateTime.now());
  }

  /**
   * Method responsible for Entity to DTO conversion.
   * 
   * @param stock as an input.
   * @return StockDto returned DTO.
   */
  public static StockDto entityToDto(Stock stock) {
    return new StockDto(stock.getStockId(), stock.getStockName(), stock.getStockPrice(), stock.getStockTimestamp());
  }
}
