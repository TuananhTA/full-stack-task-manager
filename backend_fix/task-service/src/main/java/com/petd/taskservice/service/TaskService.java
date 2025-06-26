package com.petd.taskservice.service;

import com.petd.taskservice.Projection.AssignmentBoardProjection;
import com.petd.taskservice.dto.request.AssignmentBoardRequestDto;
import com.petd.taskservice.dto.request.TaskRequestDto;
import com.petd.taskservice.dto.request.TaskUpdateName;
import com.petd.taskservice.dto.response.AssignmentBoardResponseDto;
import com.petd.taskservice.dto.response.TaskDetailsResponse;
import com.petd.taskservice.dto.response.TaskResponseDto;
import com.petd.taskservice.dto.response.UserProfileResponse;
import com.petd.taskservice.entity.AssignmentBoard;
import com.petd.taskservice.entity.AssignmentBoardId;
import com.petd.taskservice.entity.Priority;
import com.petd.taskservice.entity.Project;
import com.petd.taskservice.entity.Status;
import com.petd.taskservice.entity.Task;
import com.petd.taskservice.exception.AppException;
import com.petd.taskservice.exception.ErrorCode;
import com.petd.taskservice.mapper.TaskMapper;
import com.petd.taskservice.repository.AssignmentBoardRepository;
import com.petd.taskservice.repository.TaskRepository;
import com.petd.taskservice.repository.httpclient.ProfileClient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {

  ProfileClient profileClient;
  TaskRepository taskRepository;
  StatusService statusService;
  PriorityService priorityService;
  ProjectService projectService;
  KafkaProducerService kafkaProducerService;
  UserService userService;

  AssignmentBoardRepository assignmentBoardRepository;
  TaskMapper taskMapper;

  public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
      Task task = taskMapper.toTask(taskRequestDto);

      UserProfileResponse userProfile = getUserProfile(taskRequestDto.getCreateBy());
      if(userProfile == null) throw new AppException(ErrorCode.USER_EXISTED);
      taskRepository.save(task);
      TaskResponseDto taskResponseDto = taskMapper.toTaskResponseDto(task);
      kafkaProducerService.sendTaskEvent(taskResponseDto,"CREATE");
      return taskResponseDto;
  }
  public TaskResponseDto createEmptyTask(String statusId, String projectId) {
    Task task = new Task();

    Status status = statusService.getById(statusId);
    Project project = projectService.getProjectById(projectId);

    task.setName("Công việc mới");
    task.setProgress("0");
    task.setDescription("");
    task.setProject(project);
    task.setStatus(status);
    task.setCreateBy("9d98addf-9ec6-4c31-893e-0d6cb8a3188");
    taskRepository.save(task);
    TaskResponseDto taskResponseDto = taskMapper.toTaskResponseDto(task);
    kafkaProducerService.sendTaskEvent(taskResponseDto,"CREATE");
    return taskResponseDto;
  }



  public List<TaskResponseDto> getAllTasks() {

    List<Task> tasks = taskRepository.findAll();
    return taskMapper.toTaskResponseDtoList(tasks);
  }

  public  List<TaskResponseDto> getTaskLists(String projectId) {
    return getTasksByProjectId(projectId);
  }

  public Task getTaskById(String id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.TASK_EXISTED));
  }

  public TaskDetailsResponse getTaskDetails(String id) {
    Task task = getTaskById(id);
    return taskMapper.toTaskDetailsResponse(task);
  }


  public AssignmentBoardResponseDto assign(AssignmentBoardRequestDto requestDto, String taskId) {

      Task task = getTaskById(taskId);
      UserProfileResponse userProfile = getUserProfile(requestDto.getUserId());
      if(userProfile == null) throw new AppException(ErrorCode.USER_EXISTED);

      AssignmentBoard assignmentBoard = AssignmentBoard.builder()
          .role(requestDto.getRole())
          .id(AssignmentBoardId.builder()
              .taskId(taskId)
              .assigneeId(requestDto.getUserId())
              .build()
          )
          .build();
      assignmentBoardRepository.save(assignmentBoard);
      return AssignmentBoardResponseDto.builder()
          .taskId(taskId)
          .assignmentId(requestDto.getUserId())
          .role(requestDto.getRole())
          .build();
  }
  public List<AssignmentBoardResponseDto> getMembers (String taskId) {
      List<AssignmentBoardProjection> membersProjection = assignmentBoardRepository.getAssignmentBoardByTaskId(taskId);
      membersProjection.forEach(assignmentBoardProjection -> {
        log.info("task id {}",assignmentBoardProjection.getTaskId());
        log.info("user id {}",assignmentBoardProjection.getAssigneeId());
        log.info("role {}",assignmentBoardProjection.getRole());
      });
      return  membersProjection.stream()
          .map(item -> AssignmentBoardResponseDto.builder()
              .assignmentId(item.getAssigneeId())
              .taskId(item.getTaskId())
              .role(item.getRole())
              .build()
          )
          .toList();
  }

  public TaskResponseDto updateStatus(String taskId, String statusId) {

    Task task = getTaskById(taskId);
    Status status = statusService.getById(statusId);
    task.setStatus(status);
    taskRepository.save(task);
    TaskResponseDto taskResponseDto = taskMapper.toTaskResponseDto(task);
    kafkaProducerService.sendTaskEvent(taskResponseDto, "UPDATE");
    return taskResponseDto;
  }

  public TaskResponseDto updatePriority(String taskId, String priorityId) {
    Task task = getTaskById(taskId);
    Priority priority = priorityService.getById(priorityId);
    task.setPriority(priority);
    taskRepository.save(task);
    TaskResponseDto taskResponseDto = taskMapper.toTaskResponseDto(task);
    kafkaProducerService.sendTaskEvent(taskResponseDto, "UPDATE");
    return taskResponseDto;
  }


  public TaskResponseDto updateName(String taskId, TaskUpdateName request) {
    Task task = getTaskById(taskId);
    task.setName(request.getNewName());
    taskRepository.save(task);
    TaskResponseDto taskResponseDto = taskMapper.toTaskResponseDto(task);
    kafkaProducerService.sendTaskEvent(taskResponseDto, "UPDATE");
    return taskResponseDto;
  }


  private List<TaskResponseDto> getTasksByProjectId(String projectId){
    UserProfileResponse userProfile = userService.getXUserLoginInfoFromHeader();

    String role = userProfile.getRole();
    String departmentId = role.equals("role/Admin") ? null : userProfile.getDepartment().getId();

    List<Task> tasks = taskRepository.findInProject(
        userProfile.getId(),
        projectId,
        role,
        departmentId
    );
    return taskMapper.toTaskResponseDtoList(tasks);
  }


  private UserProfileResponse getUserProfile(String id) {
    try{
       return profileClient.getUserProfile(id);
    }catch (Exception e){
      return null;
    }
  }

}
