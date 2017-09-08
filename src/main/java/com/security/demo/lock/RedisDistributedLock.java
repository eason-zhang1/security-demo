package com.security.demo.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributedLock implements Lock{

  @Autowired
  private StringRedisTemplate redisTemplate;

  private static final String PREFIX = "redis:distribution:lock:";

  private static final long EXPIRE_TIME = 10000;

  @Override
  public boolean tryLock(String key, long timeout, TimeUnit timeUnit) {
	return false;
  }

  @Override
  public boolean lock(String key) throws InterruptedException {
    key = formatKey(key);
    if(!StringUtils.hasText(key)){
      return false;
	}

	for (int i = 0; i < 2; i ++){
	  boolean acquired = redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis() + "");
	  //是否获得锁
	  if(acquired){
		//获得锁就添加超时删除
		redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.MILLISECONDS);
		return true;
	  }else{
		//特殊情况：未加超时删除
		String thatMillisStr = redisTemplate.opsForValue().get(key);
		if(!StringUtils.hasText(thatMillisStr)){
		  //如果未获得key值，再尝试一次去设值(获得锁)
		  continue;
		}else{
		  //校验当时的时间戳是否过期
		  long thatMillis = Long.parseLong(thatMillisStr);
		  long currMillis = System.currentTimeMillis();
		  if(currMillis > thatMillis + EXPIRE_TIME){
		    //时间戳过期，但未删除, 重新设置新值
			String returnMilliSecondStr = redisTemplate.opsForValue().getAndSet(key, currMillis + "");
			//返回时间 == 之前时间 || 返回时间 is null
			if(returnMilliSecondStr == null || returnMilliSecondStr.equals(thatMillisStr)){
			  redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.MILLISECONDS);
			  return true;
			}else {
			  //未获得锁, 结束当前尝试；
			  break;
			}
		  }else {
		    //未超时， retry if times is remained
			Thread.sleep(EXPIRE_TIME/2L);
			continue;
		  }
		}
	  }
	}

	return false;
  }

  @Override
  public boolean release(String key) {
	key = formatKey(key);
	if(StringUtils.hasText(key)){
	  redisTemplate.delete(key);
	}

	return true;
  }

  private String formatKey(String key){
    if(StringUtils.hasText(key)){
      return this.PREFIX + key;
	}

	return null;
  }
}
