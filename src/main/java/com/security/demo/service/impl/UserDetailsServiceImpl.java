package com.security.demo.service.impl;

import com.security.demo.entity.User;
import com.security.demo.entity.UserRole;
import com.security.demo.jpa.UserRepository;
import com.security.demo.jpa.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
	Assert.hasText(username, "username不能为空");
	User user = userRepository.findByUsername(username);
	if(user == null){
	  throw new UsernameNotFoundException("用户不存在");
	}
	List<UserRole> userRoleList = userRoleRepository.findByUser(user);
	List<GrantedAuthority> authorities = new ArrayList<>();
	userRoleList.forEach(userRole -> authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getName())));
	user.setAuthorities(authorities);

	return user;
  }
}
