package com.security.demo.controller;

import com.security.demo.entity.User;
import com.security.demo.jpa.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class IndexController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/index")
  @Cacheable(value = "test:cache:methods", key = "#root.method.name")
  public User index(){
    return userRepository.findOne(2L);
  }
}
