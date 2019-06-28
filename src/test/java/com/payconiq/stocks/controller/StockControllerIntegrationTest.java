package com.payconiq.stocks.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stocks.model.StockDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StockControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  /**
   * Integration Test to get all stocks from DB.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testIntegration1GetAllStocks() throws Exception {
    mvc.perform(get("/api/stocks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].stockName", is("Stock1"))).andExpect(jsonPath("$[1].stockPrice", is(45.0)))
        .andExpect(jsonPath("$[2].stockName", is("Stock3"))).andExpect(jsonPath("$[3].stockId", is(4)))
        .andExpect(jsonPath("$[4].stockName", is("Stock5"))).andExpect(jsonPath("$.length()", is(5)));
  }

  /**
   * Integration Test to get a stock by StockId.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testIntegration2GetStockById() throws Exception {
    mvc.perform(get("/api/stocks/4").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("stockName", is("Stock4")));
  }

  /**
   * Integration Test to save a stock.
   * 
   * @throws Exception exception if any.
   */
  @Test
  public void testIntegration3SaveStock() throws Exception {
    StockDto stockDto = new StockDto(199, "Stock6", 77.90, null);
    mvc.perform(post("/api/stocks").content(new ObjectMapper().writeValueAsString(stockDto))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

    mvc.perform(get("/api/stocks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[5].stockName", is("Stock6")));
  }

  /**
   * Integration Test to save when already stockid is present.
   * 
   * @throws Exception possible exception if any.
   */
  @Test
  public void testIntegration4SaveStockWhenStockIdPresent() throws Exception {
    StockDto stockDto = new StockDto(5, "Stock7", 77.90, null);
    mvc.perform(post("/api/stocks").content(new ObjectMapper().writeValueAsString(stockDto))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
  }

  /**
   * Integration Test to update a stock.
   * 
   * @throws Exception possible exception if any.
   */
  @Test
  public void testIntegration5UpdateStock() throws Exception {
    String str = "{\r\n" + "    \"stockId\": 3,\r\n" + "    \"stockName\": \"Stock7\",\r\n"
        + "    \"stockPrice\": 500,\r\n" + "    \"stockTimeStamp\": \"2019-09-12T02:34:44.073\"\r\n" + "}";

    mvc.perform(put("/api/stocks/" + 3).content(str).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk());

    mvc.perform(get("/api/stocks/3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("stockPrice", is(500.0)));
  }

  /**
   * Integration test case to update a stock when stock id is not present.
   * 
   * @throws Exception possible exception if any.
   */
  @Test
  public void testIntegration6UpdateStockWhenIdNotPresent() throws Exception {
    String str = "{\r\n" + "    \"stockId\": 3,\r\n" + "    \"stockName\": \"Stock8\",\r\n"
        + "    \"stockPrice\": 592,\r\n" + "    \"stockTimeStamp\": \"2019-09-12T02:34:44.073\"\r\n" + "}";

    mvc.perform(put("/api/stocks/" + 12).content(str).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isBadRequest());
  }

  /**
   * Integration test case to delete a particular stock.
   * 
   * @throws Exception possible exception if any.
   */
  @Test
  public void testIntegration7DeleteStock() throws Exception {
    mvc.perform(delete("/api/stocks/5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    mvc.perform(get("/api/stocks/5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  /**
   * Integration test case to delete stock when ID is not present.
   * 
   * @throws Exception possible exception if any.
   */
  @Test
  public void testIntegration8DeleteWhenStockIdNotPresent() throws Exception {
    mvc.perform(delete("/api/stocks/7").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  /**
   * Integration test case to delete all stocks.
   * 
   * @throws Exception possible exception if any.
   */
  @Test
  public void testIntegration9DeleteAllStocks() throws Exception {
    mvc.perform(delete("/api/stocks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    mvc.perform(get("/api/stocks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(0)));
  }

}
