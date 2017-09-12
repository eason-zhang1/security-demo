package com.security.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.Data;

@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "async.executor")
@Data
public class AsyncConfig {

  /** Set the ThreadPoolExecutor's core pool size. */
  private int corePoolSize;
  /** Set the ThreadPoolExecutor's maximum pool size. */
  private int maxPoolSize;
  /** Set the capacity for the ThreadPoolExecutor's BlockingQueue. */
  private int queueCapacity;

  @Bean(name = "selfSimpleAsync")
  @Primary
  public Executor selfSimpleAsync() {
	ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	executor.setCorePoolSize(corePoolSize);
	executor.setMaxPoolSize(maxPoolSize);
	executor.setQueueCapacity(queueCapacity);
	executor.setThreadNamePrefix("selfSimpleExecutor-");
	executor.initialize();
	return executor;
  }

  @Bean(name = "selfAsync")
  public Executor selfAsync() {
	ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	executor.setCorePoolSize(corePoolSize);
	executor.setMaxPoolSize(maxPoolSize);
	executor.setQueueCapacity(queueCapacity);
	executor.setThreadNamePrefix("selfExecutor-");

	// rejection-policy：当pool已经达到max size的时候，如何处理新任务
	// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
	executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	executor.initialize();
	return executor;
  }
}
