package com.payconiq.stocks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for homepage.
 */
@Controller
public class HomeController {

  /**
   * Controller to start main Front End page.
   * 
   * @return String start file name.
   */
  @GetMapping("/home")
  public String home() {
    return "index";
  }
}
