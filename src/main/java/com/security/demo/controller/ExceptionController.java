package com.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ExceptionController {

  @GetMapping("/npe")
  public Map<String, String> npe(){
    throw new NullPointerException("Oops, 哈哈哈，我错了");
  }
}
