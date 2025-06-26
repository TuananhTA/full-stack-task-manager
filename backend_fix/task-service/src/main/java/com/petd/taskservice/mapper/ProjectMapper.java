package com.petd.taskservice.mapper;

import com.petd.taskservice.dto.request.ProjectRequestDto;
import com.petd.taskservice.dto.response.ProjectResponseDto;
import com.petd.taskservice.entity.Project;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectRequestDto projectRequestDto);

    ProjectResponseDto toProjectResponseDto(Project project);

    List<ProjectResponseDto> toProjectResponseDtoList(List<Project> projects);
}
