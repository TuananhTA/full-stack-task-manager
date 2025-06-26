package com.petd.taskservice.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequestDto {

  @Size(min = 6, message = "INVALID_TASK_NAME")
  @NotBlank
  String name;
  @Size(min = 6, message = "INVALID_TASK_NAME")
  String description;
  String progress;
  LocalDate startDate;
  LocalDate endDate;
  @NotBlank(message = "NOT_NULL")
  String statusId;
  String priorityId;
  String projectId;
  String createBy;
}
