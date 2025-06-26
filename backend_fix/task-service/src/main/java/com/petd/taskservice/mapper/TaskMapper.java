package com.petd.taskservice.mapper;

import com.petd.taskservice.dto.request.TaskRequestDto;
import com.petd.taskservice.dto.response.TaskDetailsResponse;
import com.petd.taskservice.dto.response.TaskResponseDto;
import com.petd.taskservice.entity.Task;
import com.petd.taskservice.helper.TaskMapperHelper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TaskMapperHelper.class)
public interface TaskMapper {

  @Mapping(target = "status", source = "statusId", qualifiedByName = "mapStatus")
  @Mapping(target = "priority", source = "priorityId", qualifiedByName = "mapPriority")
  @Mapping(target = "project", source = "projectId", qualifiedByName = "mapProject")
  Task toTask(TaskRequestDto taskRequestDto);

  @Mapping(target = "status", source = "status", qualifiedByName = "mapStatusResponse")
  @Mapping(target = "priority", source = "priority", qualifiedByName = "mapPriorityResponse")
  @Mapping(target = "creator", source = "createBy", qualifiedByName = "mapCreator")
  @Mapping(target = "projectId", source = "project", qualifiedByName = "mapProjectId")
  @Mapping(target = "assignedUsers", source = "id", qualifiedByName = "mapAssignmentUser")
  TaskResponseDto toTaskResponseDto(Task task);

  @Mapping(target = "status", source = "status", qualifiedByName = "mapStatusResponse")
  @Mapping(target = "priority", source = "priority", qualifiedByName = "mapPriorityResponse")
  @Mapping(target = "creator", source = "createBy", qualifiedByName = "mapCreator")
  @Mapping(target = "projectId", source = "project", qualifiedByName = "mapProjectId")
  TaskDetailsResponse toTaskDetailsResponse(Task task);

  List<TaskResponseDto> toTaskResponseDtoList(List<Task> tasks);
}
