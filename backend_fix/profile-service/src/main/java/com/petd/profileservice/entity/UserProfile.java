package com.petd.profileservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
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
public class UserProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @ManyToOne
  @JoinColumn(name = "department_id")
  Department department;

  @OneToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false )
  Account account;

  String firstName;
  String lastName;
  LocalDate dob;

  @CreatedDate
  @Column(updatable = false)
  LocalDateTime createdAt;

  @LastModifiedDate
  LocalDateTime updatedAt;

}
