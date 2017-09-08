package com.security.demo.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LockAop {

  @Autowired
  private RedisDistributedLock lock;


  @Around("@annotation(lockable)")
  public Object lockAround(ProceedingJoinPoint joinPoint, Lockable lockable) throws Throwable{
    boolean isLocked = lock.lock(lockable.key());
    log.debug("{}是否获得锁? -{}", Thread.currentThread().getName(), isLocked?"yes":"no");
    if(isLocked){
      try{
		return joinPoint.proceed();
	  } finally {
        lock.release(lockable.key());
	  }
	}else{
      throw new RuntimeException("当前资源正在获取中， 请稍后重试");
	}
  }
}
