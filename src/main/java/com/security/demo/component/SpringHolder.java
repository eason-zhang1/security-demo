package com.security.demo.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

public class SpringHolder implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	SpringHolder.applicationContext = applicationContext;

	Map<String, Object> restControllerBeans = applicationContext.getBeansWithAnnotation(RestController.class);
	Map<String, Object> controllerBeans = applicationContext.getBeansWithAnnotation(Controller.class);
	System.out.println("所以beanNames个数：" + (restControllerBeans.size() + controllerBeans.size()));
  }
}
