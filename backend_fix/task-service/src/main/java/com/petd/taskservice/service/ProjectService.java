package com.petd.taskservice.service;


import com.petd.taskservice.dto.request.ProjectRequestDto;
import com.petd.taskservice.dto.response.ProjectResponseDto;
import com.petd.taskservice.dto.response.UserProfileResponse;
import com.petd.taskservice.entity.Project;
import com.petd.taskservice.exception.AppException;
import com.petd.taskservice.exception.ErrorCode;
import com.petd.taskservice.mapper.ProjectMapper;
import com.petd.taskservice.repository.ProjectRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectService {

  UserService userService;
  ProjectRepository projectRepository;
  ProjectMapper projectMapper;


  public ProjectResponseDto createProject(ProjectRequestDto requestDto) {

    UserProfileResponse userProfileResponse = userService.getXUserLoginInfoFromHeader();
    String role = userProfileResponse.getRole();

    if(role.equals("role/Employee"))
      throw new AppException(ErrorCode.FORBIDDEN_ACTION);

    Project project = projectMapper.toProject(requestDto);
    if(role.equals("role/Manager")) {
      String departmentId = userProfileResponse.getDepartment().getId();
      project.setDepartmentId(departmentId);
    }
    projectRepository.save(project);
    return projectMapper.toProjectResponseDto(project);
  }

  public List<ProjectResponseDto> getAllProjects() {

    UserProfileResponse userProfileResponse = userService.getXUserLoginInfoFromHeader();
    String role = userProfileResponse.getRole();

    List<Project> projects;

    if (Objects.equals(role, "role/Admin")) {
      projects = projectRepository.findAll();
    }else if(Objects.equals(role, "role/Manager")){
      projects = projectRepository.findByDepartmentId(userProfileResponse.getDepartment().getId());
    } else if(Objects.equals(role, "role/Employee")) {
      projects = projectRepository.findByProjectUserId(userProfileResponse.getId());
    }else{
      throw new AppException(ErrorCode.INVALID_KEY);
    }

    return projectMapper.toProjectResponseDtoList(projects);
  }

  public Project getProjectById(String id) {
    return  projectRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXIST));
  }


}
