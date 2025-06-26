package com.petd.taskservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Priority {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
  String name;

  String color;

  @Column
  Integer density;

  @OneToMany(mappedBy = "priority")
  List<Task> tasks = new ArrayList<>();

  @CreationTimestamp
  @Column
  LocalDateTime createdAt;

  @UpdateTimestamp
  LocalDateTime updatedAt;

}
