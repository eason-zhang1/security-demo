package com.security.demo.jpa;

import com.security.demo.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaSpecificationExecutor<Role>, JpaRepository<Role, Long> {

  Role findByName(String user);
}
