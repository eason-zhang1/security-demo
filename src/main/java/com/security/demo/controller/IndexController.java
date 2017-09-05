package com.security.demo.controller;

import com.security.demo.entity.User;
import com.security.demo.jpa.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/public")
public class IndexController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MessageSource messageSource;

  @GetMapping("/index")
  @Cacheable(value = "test:cache:methods", key = "#root.method.name")
  public User index(){
    return userRepository.findOne(2L);
  }

  @GetMapping("/index2")
  public String index2(){
    Locale locale = LocaleContextHolder.getLocale();//zh_CN
    return messageSource.getMessage("welcome", null, null);
  }
}
