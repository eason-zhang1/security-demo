package com.security.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.annotation.TestAnn;
import com.security.demo.config.BeanConfig.Self;
import com.security.demo.entity.User;
import com.security.demo.jpa.UserRepository;
import com.security.demo.lock.Lockable;
import com.security.demo.service.AsyncService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/public")
@Slf4j
public class IndexController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private Self self;

  @Autowired
  private AsyncService asyncService;

  @GetMapping("/index")
  @Cacheable(value = "test:cache:methods", key = "#root.method.name")
  public String index() throws JsonProcessingException {
	User user = userRepository.findOne(2L);
	if (user != null) {
	  return objectMapper.writeValueAsString(user);
	} else {
	  return null;
	}
  }

  @GetMapping("/index2")
  @TestAnn("哈哈哈")
  public String index2() {
	Locale locale = LocaleContextHolder.getLocale();//zh_CN
	return messageSource.getMessage("welcome", null, null);
  }

  @GetMapping("/index3")
  public String index3() throws JsonProcessingException {
	return objectMapper.writeValueAsString(self);
  }

  @GetMapping("/index4")
  @Lockable(key = "index4")
  public String index4() throws Exception {
    Thread.sleep(7000);
	return objectMapper.writeValueAsString(self);
  }

  @GetMapping("/index5")
  public Map<String, String> index5() throws Exception {
	Future<Map<String, String>> future = asyncService.getTask();
    while(true){
      log.info("time is {}", LocalDateTime.now());
      if(future.isDone()){
		log.info(" ---------task is done");
        return future.get();
	  }else{
        log.info(" ---------task is not done");
	  }

	  Thread.sleep(5000);
	}
  }
}
