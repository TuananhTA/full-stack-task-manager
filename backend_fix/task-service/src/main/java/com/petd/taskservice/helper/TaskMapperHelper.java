package com.petd.taskservice.helper;

import com.petd.taskservice.dto.response.PriorityResponseDto;
import com.petd.taskservice.dto.response.StatusResponseDto;
import com.petd.taskservice.dto.response.UserProfileResponse;
import com.petd.taskservice.entity.Priority;
import com.petd.taskservice.entity.Project;
import com.petd.taskservice.entity.Status;
import com.petd.taskservice.mapper.PriorityMapper;
import com.petd.taskservice.mapper.StatusMapper;
import com.petd.taskservice.repository.httpclient.ProfileClient;
import com.petd.taskservice.service.AssignmentBoardService;
import com.petd.taskservice.service.PriorityService;
import com.petd.taskservice.service.ProjectService;
import com.petd.taskservice.service.StatusService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskMapperHelper {

  StatusService statusService;
  PriorityService priorityService;
  ProjectService projectService;
  ProfileClient profileClient;
  AssignmentBoardService assignmentBoardService;

  PriorityMapper priorityMapper;
  StatusMapper statusMapper;

  @Named("mapStatus")
  public Status mapStatus(String id) {
    return statusService.getById(id);
  }

  @Named("mapPriority")
  public Priority mapPriority(String id) {
    try {
      return priorityService.getById(id);
    }catch ( Exception e){
      return null;
    }
  }


  @Named("mapStatusResponse")
  public StatusResponseDto mapStatusResponse(Status status) {
    return statusMapper.toStatusResponseDto(status);
  }

  @Named("mapPriorityResponse")
  public PriorityResponseDto mapPriorityResponse(Priority priority) {
    return priorityMapper.toPriorityResponseDto(priority);
  }
  @Named("mapCreator")
  public UserProfileResponse mapCreator(String id) {
    try {
      return profileClient.getUserProfile(id);
    }catch ( Exception e){
      return null;
    }
  }

  @Named("mapProjectId")
  public String mapProjectId(Project project) {
    if(project == null) { return null; }
    return project.getId();
  }
  @Named("mapProject")
  public Project mapProject(String id) {
    if(id == null) { return null; }
    return projectService.getProjectById(id);
  }

  @Named("mapAssignmentUser")
  public List<UserProfileResponse> mapAssignmentUser(String id) {
    return assignmentBoardService.assignedUsersByTask(id);
  }
}
