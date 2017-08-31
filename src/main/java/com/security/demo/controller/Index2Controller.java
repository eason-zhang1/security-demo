package com.security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mgt")
public class Index2Controller {

  @GetMapping("/index")
  @PreAuthorize("hasRole('ROLE_USER')")
  public String index(){
	return "index2";
  }
}
