package com.security.demo.lock;

	import java.util.concurrent.TimeUnit;

public interface Lock {

  boolean tryLock(String key, long timeout, TimeUnit timeUnit);

  boolean lock(String key) throws InterruptedException;

  boolean release(String key);
}
