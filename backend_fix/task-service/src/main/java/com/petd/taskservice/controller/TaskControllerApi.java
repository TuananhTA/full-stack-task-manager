package com.petd.taskservice.controller;

import com.petd.taskservice.dto.request.AssignmentBoardRequestDto;
import com.petd.taskservice.dto.request.TaskRequestDto;
import com.petd.taskservice.dto.request.TaskUpdateName;
import com.petd.taskservice.dto.response.ApiResponse;
import com.petd.taskservice.dto.response.AssignmentBoardResponseDto;
import com.petd.taskservice.dto.response.TaskDetailsResponse;
import com.petd.taskservice.dto.response.TaskResponseDto;
import com.petd.taskservice.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskControllerApi {

  TaskService taskService;

  @PostMapping
  public ApiResponse<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto) {
    return ApiResponse.<TaskResponseDto>builder()
        .result(taskService.createTask(taskRequestDto))
        .build();
  }

  @PostMapping("/empty-task/{statusId}/{projectId}")
  public ApiResponse<TaskResponseDto> createEmptyTask(
      @PathVariable(name = "statusId") String statusId,
      @PathVariable(name = "projectId") String projectId
  ) {
    return ApiResponse.<TaskResponseDto>builder()
        .result(taskService.createEmptyTask(statusId, projectId))
        .build();
  }

  @GetMapping("/get-task-list/{projectId}")
  public ApiResponse<List<TaskResponseDto>> getTaskList(@PathVariable String projectId) {
    return ApiResponse.<List<TaskResponseDto>>builder()
        .result(taskService.getTaskLists(projectId))
        .build();
  }
  @GetMapping("/{taskId}")
  public ApiResponse<TaskDetailsResponse> getTask(@PathVariable String taskId) {
    return ApiResponse.<TaskDetailsResponse>builder()
        .result(taskService.getTaskDetails(taskId))
        .build();
  }

  @PutMapping("/{taskId}/members")
  public ApiResponse<AssignmentBoardResponseDto> addMembers(
      @RequestBody @Valid AssignmentBoardRequestDto requestDto,
      @PathVariable String taskId)
  {
    return ApiResponse.<AssignmentBoardResponseDto>builder()
        .result(taskService.assign(requestDto, taskId))
        .build();
  }
  @GetMapping("{taskId}/members")
  public ApiResponse<List<AssignmentBoardResponseDto>> getMembers(@PathVariable String taskId){
      return ApiResponse.<List<AssignmentBoardResponseDto>>builder()
          .result(taskService.getMembers(taskId))
          .build();
  }

  @PutMapping("{taskId}/{statusId}/change-status")
  public ApiResponse<TaskResponseDto> changeStatus(
        @PathVariable(name = "statusId") String statusId,
        @PathVariable(name = "taskId") String taskId){
    return ApiResponse.<TaskResponseDto>builder()
        .result(taskService.updateStatus(taskId, statusId))
        .build();
  }

  @PutMapping("{taskId}/{priorityId}/change-priority")
  public ApiResponse<TaskResponseDto> changePriority(
      @PathVariable(name = "priorityId") String priorityId,
      @PathVariable(name = "taskId") String taskId){
    return ApiResponse.<TaskResponseDto>builder()
        .result(taskService.updatePriority(taskId, priorityId))
        .build();
  }

  @PutMapping("{taskId}/change-name")
  public ApiResponse<TaskResponseDto> changeName(
      @PathVariable(name = "taskId") String taskId,
      @RequestBody @Valid TaskUpdateName request
  ){
    return ApiResponse.<TaskResponseDto>builder()
        .result(taskService.updateName(taskId, request))
        .build();
  }

}
