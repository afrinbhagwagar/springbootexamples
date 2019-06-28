package com.payconiq.stocks.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stocks.exceptions.InvalidInputException;
import com.payconiq.stocks.model.StockDto;
import com.payconiq.stocks.service.StockService;
import com.payconiq.stocks.utils.StockConverter;

@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private StockService stockService;

  /**
   * Test case to get all stocks when data correctly present in db.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testGetAllStocksWithCorrectData() throws Exception {
    StockDto stockDto1 = new StockDto(123, "Stock1", 200.50, LocalDateTime.parse("2018-06-24T21:30:23.073"));
    StockDto stockDto2 = new StockDto(456, "Stock2", 80, LocalDateTime.parse("2017-07-07T08:11:55.011"));
    StockDto stockDto3 = new StockDto(382, "Stock3", 20000, LocalDateTime.parse("2019-08-12T01:15:55.073"));

    List<StockDto> listOfStock = new ArrayList<>();
    listOfStock.add(stockDto1);
    listOfStock.add(stockDto2);
    listOfStock.add(stockDto3);
    when(stockService.getAllStocks()).thenReturn(listOfStock);

    mvc.perform(get("/api/stocks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].stockName", is(listOfStock.get(0).getStockName())))
        .andExpect(jsonPath("$[1].stockPrice", is(listOfStock.get(1).getStockPrice())));

  }

  /**
   * Test case to get stock by id. Positive flow.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testGetStockByIdWithCorrectInputs() throws Exception {
    StockDto stockDto1 = new StockDto(123, "Stock1", 200.50, LocalDateTime.parse("2018-06-24T21:30:33.073"));

    when(stockService.getStockById(123)).thenReturn(stockDto1);
    mvc.perform(get("/api/stocks/123").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("stockName", is(stockDto1.getStockName())));
  }

  /**
   * Test case to get stock by id when Stock Id is not present.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testGetStockByIdWhenIdNotPresent() throws Exception {

    when(stockService.getStockById(123)).thenThrow(EntityNotFoundException.class);
    mvc.perform(get("/api/stocks/123").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  /**
   * Test case to save stock.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testSaveStock() throws Exception {
    String str = "{\r\n" + "    \"stockId\": 150,\r\n" + "    \"stockName\": \"Stock1\",\r\n"
        + "    \"stockPrice\": 550.45\r\n" + "}";
    ObjectMapper mapper = new ObjectMapper();
    StockDto stockDto = mapper.readValue(str, StockDto.class);
    when(stockService.saveStock(any(StockDto.class))).thenReturn(StockConverter.dtoToEntity(stockDto));
    MvcResult result = mvc
        .perform(
            post("/api/stocks").content(str).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated()).andReturn();
    // Only for localhost
    assertEquals("http://localhost/api/stocks/150", result.getResponse().getRedirectedUrl());
  }

  /**
   * Test case to update stock when stock present.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testUpdateStockConsideringStockPresent() throws Exception {
    String str = "{\r\n" + "    \"stockId\": 123,\r\n" + "    \"stockName\": \"Stock1\",\r\n"
        + "    \"stockPrice\": 200.50\r\n" + "}";
    ObjectMapper mapper = new ObjectMapper();
    StockDto stockDto = mapper.readValue(str, StockDto.class);
    // Considering if stock changed by this service call.
    when(stockService.updateStock(any(StockDto.class), any(Long.class)))
        .thenReturn(StockConverter.dtoToEntity(stockDto));
    mvc.perform(put("/api/stocks/" + 123).content(str).accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

  }

  /**
   * Test case to update stock when invalid stock id is present.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testUpdateStockWhenInvalidStockId() throws Exception {
    String str = "{\r\n" + "    \"stockId\": 123,\r\n" + "    \"stockName\": \"Stock1\",\r\n"
        + "    \"stockPrice\": 200.50\r\n" + "}";
    when(stockService.updateStock(any(StockDto.class), any(Long.class))).thenThrow(InvalidInputException.class);
    mvc.perform(put("/api/stocks/" + 123).content(str).accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }
}
