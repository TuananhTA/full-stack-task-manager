package com.petd.taskservice.controller;


import com.petd.taskservice.dto.request.ProjectRequestDto;
import com.petd.taskservice.dto.response.ApiResponse;
import com.petd.taskservice.dto.response.ProjectResponseDto;
import com.petd.taskservice.service.ProjectService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectControllerApi {

  ProjectService projectService;

  @PostMapping
  public ApiResponse<ProjectResponseDto> createProject(@RequestBody ProjectRequestDto projectRequestDto) {
    return ApiResponse.<ProjectResponseDto>builder()
        .result(projectService.createProject(projectRequestDto))
        .build();
  }
  @GetMapping
  public ApiResponse<List<ProjectResponseDto>> getAllProjects() {
    return ApiResponse.<List<ProjectResponseDto>>builder()
        .result(projectService.getAllProjects())
        .build();
  }

}
