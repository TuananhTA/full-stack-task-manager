package com.petd.websocketservice.dto.response;

import java.util.ArrayList;
import java.util.List;
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
public class TaskListResponse {
  List<StatusResponseDto> statuses = new ArrayList<>();
  List<TaskResponseDto> tasksByStatus = new ArrayList<>();
}
