package com.security.demo.jpa;

import com.security.demo.entity.User;
import com.security.demo.entity.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaSpecificationExecutor<UserRole>, JpaRepository<UserRole, Long> {

  List<UserRole> findByUser(User user);
}
