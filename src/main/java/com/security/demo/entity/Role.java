package com.security.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role extends PersistenceEntity<Long> {

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "is_active")
  private boolean active = true;
}
