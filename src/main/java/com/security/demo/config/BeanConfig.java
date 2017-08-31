package com.security.demo.config;

import com.security.demo.entity.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class BeanConfig {

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
}
