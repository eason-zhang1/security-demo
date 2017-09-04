package com.security.demo.controller;

import com.security.demo.entity.User;
import com.security.demo.service.UserService;
import com.security.demo.utils.MsgUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/public")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @PostMapping("/user/login")
  public Map<String, String> login(@RequestBody Map<String, String> loginBody, HttpServletRequest request){

    //其他信息校验。。。

	UserDetails userDetails = userDetailsService.loadUserByUsername(loginBody.get("username"));
	final UsernamePasswordAuthenticationToken token
		= new UsernamePasswordAuthenticationToken(loginBody.get("username"),
												  loginBody.get("password"));

	final Authentication authentication = this.authenticationManager.authenticate(token);

	final HttpSession session = request.getSession(true);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	/*session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
						 SecurityContextHolder.getContext());*/
	//cookie data...
	return MsgUtil.getMsg("成功:" + session.getId());
  }

  @PostMapping("/user/register")
  public User register(@RequestBody Map<String, String> loginBody){
	User user = new User();
	user.setActive(true);
	user.setPassword(passwordEncoder.encode(loginBody.get("password")));
	user.setUsername(loginBody.get("username"));
	user = userService.register(user);

	return user;
  }

  @GetMapping("/expire")
  public void expire(HttpServletRequest request){
    request.getSession().invalidate();
  }
}
