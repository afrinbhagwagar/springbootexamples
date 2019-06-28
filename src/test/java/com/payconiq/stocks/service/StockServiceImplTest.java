package com.payconiq.stocks.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.exceptions.InvalidInputException;
import com.payconiq.stocks.model.StockDto;
import com.payconiq.stocks.repository.StockRepository;
import com.payconiq.stocks.utils.StockConverter;

@RunWith(SpringRunner.class)
public class StockServiceImplTest {

  @TestConfiguration
  static class StockServiceImplTestConfiguration {

    @Bean
    public StockService stockService() {
      return new StockServiceImpl();
    }
  }

  @Autowired
  private StockService stockService;

  @MockBean
  private StockRepository stockRepository;

  /**
   * Test case to get stock by id. Positive flow.
   */
  @Test
  public void testgetStockByIdWithCorrectInput() {
    Stock stock = new Stock(123, "Stock1", 200.50, LocalDateTime.parse("2016-06-24T21:30:33.073"));
    Mockito.when(stockRepository.getOne(stock.getStockId())).thenReturn(stock);

    StockDto stockDto = stockService.getStockById(123);
    assertEquals("Stock1", stockDto.getStockName());
    assertEquals(200.50, stockDto.getStockPrice(), 0.0);
  }

  /**
   * Test case to get stock by id. When StockId is not present.
   */
  @Test(expected = EntityNotFoundException.class)
  public void testgetStockByIdWhenStockIdIsNotPresent() {
    Stock stock = new Stock(123, "Stock2", 200.50, LocalDateTime.parse("2018-02-24T21:30:33.073"));
    Mockito.when(stockRepository.getOne(stock.getStockId())).thenThrow(EntityNotFoundException.class);

    stockService.getStockById(123);
  }

  /**
   * Test case to get all stocks.
   */
  @Test
  public void testgetAllStocksWithCorrectInput() {
    Stock stock1 = new Stock(123, "Stock1", 200.50, LocalDateTime.parse("2018-06-24T21:30:33.073"));
    Stock stock2 = new Stock(456, "Stock2", 80, LocalDateTime.parse("2017-07-07T08:11:55.011"));
    Stock stock3 = new Stock(382, "Stock3", 20000, LocalDateTime.parse("2019-08-12T01:15:55.073"));

    List<Stock> listOfStock = new ArrayList<>();
    listOfStock.add(stock1);
    listOfStock.add(stock2);
    listOfStock.add(stock3);

    Mockito.when(stockRepository.findAll()).thenReturn(listOfStock);

    List<StockDto> listOfStockDto = stockService.getAllStocks();
    assertEquals(3, listOfStockDto.size());
    assertEquals(123, listOfStockDto.get(0).getStockId());
    assertEquals("Stock2", listOfStockDto.get(1).getStockName());
    assertEquals(20000, listOfStockDto.get(2).getStockPrice(), 0.0);
  }

  /**
   * Test case to save stock when stock id is present.
   */
  @Test(expected = InvalidInputException.class)
  public void testSaveStockWithStockIdPresent() {
    Stock stock1 = new Stock(123, "Stock1", 200.50, LocalDateTime.parse("2018-06-24T21:30:33.073"));
    Mockito.when(stockRepository.findById(any(Long.class))).thenReturn(Optional.of(stock1));

    stockService.saveStock(StockConverter.entityToDto(stock1));
  }

  /**
   * Test case to save stock when that id is not present. Thus as id is not present it would be saved.
   */
  @Test
  public void testSaveStockAsNewOneWithNoStockIdPresent() {
    Stock stock1 = new Stock(123, "Stock1", 200.50, LocalDateTime.parse("2018-06-24T21:30:33.073"));
    Mockito.when(stockRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    Mockito.when(stockRepository.save(any(Stock.class))).thenReturn(stock1);

    Stock savedStock = stockService.saveStock(StockConverter.entityToDto(stock1));
    assertEquals("Stock1", savedStock.getStockName());
    assertEquals(200.50, savedStock.getStockPrice(), 0);
  }

  /**
   * Test case to update stock when stock not present. Checks if exception is thrown.
   */
  @Test(expected = InvalidInputException.class)
  public void testUpdateStockWhenNoStockIdPresent() {
    Stock stock1 = new Stock(123, "Stock1", 200.50, LocalDateTime.parse("2018-06-24T21:30:33.073"));
    Mockito.when(stockRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    stockService.updateStock(StockConverter.entityToDto(stock1), stock1.getStockId());
  }

  /**
   * Test case to update stock when stock id is present.
   */
  @Test
  public void testUpdateStockWhenStockIdPresent() {
    Stock stock1 = new Stock(123, "Stock1", 200.50, LocalDateTime.parse("2018-06-24T21:30:33.073"));
    Stock stock2 = new Stock(123, "Stock2", 200.50, LocalDateTime.parse("2018-06-24T21:30:33.073"));
    Mockito.when(stockRepository.findById(any(Long.class))).thenReturn(Optional.of(stock1));
    Mockito.when(stockRepository.save(any(Stock.class))).thenReturn(stock2);

    Stock savedStock = stockService.updateStock(StockConverter.entityToDto(stock1), 123);
    assertEquals("Stock2", savedStock.getStockName());
    assertEquals(200.50, savedStock.getStockPrice(), 0);
  }
}
