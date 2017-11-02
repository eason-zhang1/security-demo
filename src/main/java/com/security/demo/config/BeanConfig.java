package com.security.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.component.AnnotationScannerComponent;
import com.security.demo.component.SpringHolder;
import com.security.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BeanConfig {

  @Autowired
  private ObjectMapper objectMapper;

  @Bean("securityAuditor")
  public AuditorAware<String> auditor(){
	return () -> {
	  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	  if (authentication == null || !authentication.isAuthenticated()) {
		return null;
	  }
	  if(authentication instanceof AnonymousAuthenticationToken){
	    return null;
	  }
	  return ((User) authentication.getPrincipal()).getUsername();
	};
  }

  /*@Bean(name = "objectRedisTemplate")
  public RedisTemplate<Object, Object> objectRedisTemplate(RedisConnectionFactory factory){
	StringRedisSerializer stringSerializer = new StringRedisSerializer();
	Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
	jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

    RedisTemplate<Object, Object> objectRedisTemplate = new RedisTemplate<>();
    objectRedisTemplate.setConnectionFactory(factory);
	objectRedisTemplate.setKeySerializer(stringSerializer);
	objectRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
	objectRedisTemplate.setHashKeySerializer(stringSerializer);
	objectRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
	objectRedisTemplate.afterPropertiesSet();

	return objectRedisTemplate;
  }*/

  @Data
  @Component
  @ConfigurationProperties(prefix = "self")
  public static class Self{

    private String id;
    private String name;
    private String home;

  }

  @Bean
  @Order
  public AnnotationScannerComponent initComponent(){
    log.info("init.... component");
    return new AnnotationScannerComponent();
  }

  @Bean
  public SpringHolder initSpringHolder(){
    log.info("init spring holder component");
    return new SpringHolder();
  }
}
