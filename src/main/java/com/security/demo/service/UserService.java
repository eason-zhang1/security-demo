package com.security.demo.service;

import com.security.demo.entity.User;

import java.util.Map;

public interface UserService {

  Map<String, String> login(Map<String, String> loginBody);

  User register(User user);
}
