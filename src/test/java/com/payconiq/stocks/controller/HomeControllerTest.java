package com.payconiq.stocks.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

  @Autowired
  private MockMvc mvc;

  /**
   * Home controller test case
   * 
   * @throws Exception possible exception if any.
   */
  @Test
  public void testIntegration1GetAllStocks() throws Exception {
    MvcResult result = mvc.perform(get("/home")).andExpect(status().isOk()).andExpect(status().isOk()).andReturn();
    assertEquals("/views/index.html", result.getResponse().getForwardedUrl());
  }
}
