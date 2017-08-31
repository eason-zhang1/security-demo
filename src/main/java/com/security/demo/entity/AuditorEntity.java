package com.security.demo.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditorEntity<PK> extends PersistenceEntity<PK> {

  @CreatedDate
  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @CreatedBy
  @Column(name = "create_user")
  private String createdUser;

  @LastModifiedDate
  @Column(name = "modified_date")
  private LocalDateTime modifiedDate;

  @LastModifiedBy
  @Column(name = "modified_user")
  private String modifiedUser;
}
