package com.payconiq.stocks.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;

import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.model.StockDto;

public class StockConverterTest {

  /**
   * Test case to check for conversion from entity to dto.
   */
  @Test
  public void testEntityToDtoConverter() {

    Stock stockEntity = new Stock(123, "Stock4", 200.50, LocalDateTime.parse("2018-06-24T21:30:33.073"));
    StockDto stockDto = StockConverter.entityToDto(stockEntity);
    assertEquals(123, stockDto.getStockId());
    assertEquals("Stock4", stockDto.getStockName());
    assertEquals(200.50, stockDto.getStockPrice(), 0);
    assertEquals(LocalDateTime.parse("2018-06-24T21:30:33.073"), stockDto.getStockTimestamp());
  }

  /**
   * Test case to check for conversion from dto to entity.
   */
  @Test
  public void testDtoToEntityConverter() {

    StockDto stockDto = new StockDto();
    stockDto.setStockId(123);
    stockDto.setStockName("Stock5");
    stockDto.setStockPrice(200.50);

    Stock stockEntity = StockConverter.dtoToEntity(stockDto);
    assertEquals(123, stockEntity.getStockId());
    assertEquals("Stock5", stockEntity.getStockName());
    assertEquals(200.50, stockEntity.getStockPrice(), 0);
    assertNotNull(stockEntity.getStockTimestamp());
  }
}
