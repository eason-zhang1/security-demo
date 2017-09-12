package com.security.demo.service;

import com.google.common.collect.Maps;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsyncService {

  @Async("selfAsync")
  public Future<Map<String, String>> getTask() throws Exception {
    log.info("task is starting.{}", LocalDateTime.now());
    Map<String, String> result = Maps.newHashMap();
    for (int i = 0; i < 2; i ++){
      Thread.sleep(10000);
      result.put("key-" + i, "value-" + i);
	}
	log.info("task is end. {}", LocalDateTime.now());

    return new AsyncResult<>(result);
  }
}
