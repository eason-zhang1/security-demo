package com.security.demo.component;

import com.security.demo.annotation.TestAnn;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnnotationScannerComponent implements BeanDefinitionRegistryPostProcessor {

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    Map<String, Object> beans = configurableListableBeanFactory.getBeansWithAnnotation(RestController.class);
	beans.forEach((beanName, bean) -> {
	  log.info("啦啦啦啦。。。{}->{}", beanName, bean);
	  Class clazz = bean.getClass();
	  Method[] methods = clazz.getMethods();
	  Arrays.asList(methods).forEach(method -> {
	    TestAnn testAnn = method.getAnnotation(TestAnn.class);
	    if(testAnn != null){
	      log.info("哈哈哈哈，，，， {}->{}->{}", beanName, method.getName(), testAnn.value());
		}
	  });
	});
  }
}
