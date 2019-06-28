package com.payconiq.stocks.controller;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.exceptions.StockNotFoundException;
import com.payconiq.stocks.model.StockDto;
import com.payconiq.stocks.service.StockService;
import com.payconiq.stocks.service.StockServiceImpl;

/**
 * Controllers for GET, POST, PUT, DELETE calls. Manages all stock data.
 *
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {

  @Autowired
  private StockService stockService;

  private Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

  /**
   * get controller to get all stocks.
   * 
   * @return List list of all Stocks present in database.
   */
  @GetMapping
  public List<StockDto> getAllStocks() {
    return stockService.getAllStocks();
  }

  /**
   * get controller to get stock based on key.
   * 
   * @param stockId id representing which stock should be fetched.
   * @return StockDto a particular stock based on the stockId present.
   */
  @GetMapping("/{stockId}")
  public StockDto getStockById(@PathVariable long stockId) {
    StockDto stockDto = null;
    try {
      stockDto = stockService.getStockById(stockId);
    } catch (EntityNotFoundException enfe) {
      throw new StockNotFoundException(enfe.getMessage());
    }
    return stockDto;
  }

  /**
   * post controller to save stocks.
   * 
   * @param stockDto a particular stock to be saved in database.
   * @return ResponseEntity location url sent if saved.
   */
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<Void> saveStock(
      @RequestBody @NotNull(message = "Stock instance not available") StockDto stockDto) {
    Stock savedStock = stockService.saveStock(stockDto);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedStock.getStockId()).toUri();
    return ResponseEntity.created(location).build();
  }

  /**
   * put controller to update a particular stock.
   * 
   * @param stockDto dto sent which has to be updated.
   * @param stockId stockId passed which has to be updated.
   * @return ResponseEntity stock which has been updated.
   */
  @PutMapping("/{stockId}")
  public ResponseEntity<Stock> updateStock(
      @RequestBody @NotNull(message = "Stock instance not available") StockDto stockDto, @PathVariable long stockId) {
    return ResponseEntity.ok(stockService.updateStock(stockDto, stockId));
  }

  /**
   * delete controller to delete all stocks.
   */
  @DeleteMapping
  public void deleteAllStocks() {
    stockService.deleteAllStocks();
  }

  /**
   * delete controller to delete a particular stock.
   * 
   * @param stockId delete by stockId.
   */
  @DeleteMapping("/{stockId}")
  public void deleteStockById(@PathVariable long stockId) {
    try {
      stockService.deleteStockById(stockId);
    } catch (EmptyResultDataAccessException erdae) {
      logger.error("Stock ID not present to delete. Throwing StockNotFoundException.");
      throw new StockNotFoundException(erdae.getMessage());
    }
  }

}
