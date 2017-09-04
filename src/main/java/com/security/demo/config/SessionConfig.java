package com.security.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * details: http://blog.csdn.net/zl18310999566/article/details/54290994
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = -1)
public class SessionConfig {

  @Bean
  public HttpSessionStrategy httpSessionStrategy() {
	return new HeaderHttpSessionStrategy();
  }
}
