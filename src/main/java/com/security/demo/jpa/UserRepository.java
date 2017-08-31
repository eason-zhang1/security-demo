package com.security.demo.jpa;

import com.security.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, Long>{

  User findByUsername(String username);
}
