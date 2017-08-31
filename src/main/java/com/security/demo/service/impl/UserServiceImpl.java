package com.security.demo.service.impl;

import com.security.demo.entity.Role;
import com.security.demo.entity.User;
import com.security.demo.entity.UserRole;
import com.security.demo.jpa.RoleRepository;
import com.security.demo.jpa.UserRepository;
import com.security.demo.jpa.UserRoleRepository;
import com.security.demo.service.UserService;
import com.security.demo.utils.MsgUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Map<String, String> login(Map<String, String> loginBody) {
    Map<String, String> result = MsgUtil.getMsg("success");
    result.put("username", "哈哈哈");
    result.put("password", "578787548");

    return result;
  }

  @Override
  public User register(User user) {
    user = userRepository.save(user);

    Role role = roleRepository.findByName("USER");
    UserRole userRole = new UserRole();
    userRole.setRole(role);
    userRole.setUser(user);
    userRoleRepository.save(userRole);

    return user;
  }


}
