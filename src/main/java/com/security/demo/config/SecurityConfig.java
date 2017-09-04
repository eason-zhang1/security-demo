package com.security.demo.config;

import com.security.demo.service.impl.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)//开启注解
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  RedisOperationsSessionRepository redisOperationsSessionRepository;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
  }

  @Bean
  public SpringSessionBackedSessionRegistry sessionRegistry(){
	return new SpringSessionBackedSessionRegistry((FindByIndexNameSessionRepository) this.redisOperationsSessionRepository);
  }

  //对每个请求进行细粒度安全性控制的关键在于重载一下方法
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	http.cors().and()
		.csrf()
		.disable()
		.headers()
		.frameOptions()
		.disable()
		.and()
		//只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
		.sessionManagement().maximumSessions(1)
		.expiredUrl("/api/public/login").sessionRegistry(sessionRegistry());

	http.authorizeRequests()
		.antMatchers("/api/public/**").permitAll()
		.antMatchers("/api/mgt/**").authenticated();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
	//忽略web相关的请求
	web.ignoring().antMatchers("/**/*.html",//
							   "/css/**",//
							   "/js/**",//
							   "/img/**",//
							   "/**/*.ico");
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	return passwordEncoder;
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
  }
}
