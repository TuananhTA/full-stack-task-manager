package com.petd.profileservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account{


  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private UserProfile userProfile;

  @Column(nullable = false, unique = true)
  String email;

  String password;
  String role;
  boolean isActive = true;

  @CreatedDate
  @Column(updatable = false)
  LocalDateTime createdAt;

  @LastModifiedDate
  LocalDateTime updatedAt;

}
