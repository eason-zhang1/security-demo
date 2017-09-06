package com.security.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.config.BeanConfig.Self;
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

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private Self self;

  @GetMapping("/index")
  @Cacheable(value = "test:cache:methods", key = "#root.method.name")
  public String index() throws JsonProcessingException {
    User user = userRepository.findOne(2L);
    if(user != null){
      return objectMapper.writeValueAsString(user);
    }else {
      return null;
    }
  }

  @GetMapping("/index2")
  public String index2(){
    Locale locale = LocaleContextHolder.getLocale();//zh_CN
    return messageSource.getMessage("welcome", null, null);
  }

  @GetMapping("/index3")
  public String index3() throws JsonProcessingException {
    return objectMapper.writeValueAsString(self);
  }
}
