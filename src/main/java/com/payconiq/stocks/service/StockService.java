package com.payconiq.stocks.service;

import java.util.List;

import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.model.StockDto;

public interface StockService {

  /**
   * Get all stocks.
   * 
   * @return List list of all Stocks present in database.
   */
  List<StockDto> getAllStocks();

  /**
   * Get a stock by Id.
   * 
   * @param stockId id representing which stock should be fetched.
   * @return StockDto a particular stock based on the stockId present.
   */
  StockDto getStockById(long stockId);

  /**
   * Save stocks.
   * 
   * @param stockDto a particular stock to be saved in database.
   * @return Stock what has been saved.
   */
  Stock saveStock(StockDto stock);

  /**
   * Update a particular stock.
   * 
   * @param stockDto dto sent which has to be updated.
   * @param stockId stockId passed which has to be updated.
   * @return Stock which has been updated.
   */
  Stock updateStock(StockDto stockDto, long stockId);

  /**
   * Delete all stocks.
   */
  void deleteAllStocks();

  /**
   * delete controller to delete a particular stock.
   * 
   * @param stockId delete by stockId.
   */
  void deleteStockById(long stockId);

}
