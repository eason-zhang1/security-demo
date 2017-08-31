package com.security.demo.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String errMsg) {
	super(errMsg);
  }
}
