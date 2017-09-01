package com.security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisCacheConfig implements CachingConfigurer{

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  @Override
  public CacheManager cacheManager() {
	RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
	cacheManager.setDefaultExpiration(3600L);
	cacheManager.setUsePrefix(true);
	return cacheManager;
  }

  @Override
  public CacheResolver cacheResolver() {
	return new SimpleCacheResolver(cacheManager());
  }

  @Override
  public KeyGenerator keyGenerator() {
	return new SimpleKeyGenerator();
  }

  @Override
  public CacheErrorHandler errorHandler() {
	return new SimpleCacheErrorHandler();
  }
}
