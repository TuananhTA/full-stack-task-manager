package com.petd.taskservice.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @Column(nullable = false)
  String name;
  @Column(columnDefinition = "TEXT")
  String description;
  @Column
  String progress;
  @Column
  LocalDate startDate;
  @Column
  LocalDate endDate;
  @CreationTimestamp
  @Column
  LocalDateTime createdAt;
  @UpdateTimestamp
  LocalDateTime updatedAt;
  @Column(nullable = false)
  String createBy;

  @ManyToOne
  @JoinColumn(name = "status_id")
  Status status;

  @ManyToOne
  @JoinColumn(name = "priority_id")
  Priority priority;

  @OneToMany(mappedBy = "task")
  List<SubTask> subTasks = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "project_id")
  Project project;

  @OneToMany(mappedBy = "toTask")
  List<Message> messages = new ArrayList<>();

}
