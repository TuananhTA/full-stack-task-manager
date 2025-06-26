package com.petd.taskservice.mapper;

import com.petd.taskservice.dto.request.PriorityRequestDto;
import com.petd.taskservice.dto.response.PriorityResponseDto;
import com.petd.taskservice.entity.Priority;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriorityMapper {

  Priority toPriority (PriorityRequestDto projectRequestDto);

  PriorityResponseDto toPriorityResponseDto(Priority priority);

  List<PriorityResponseDto> toPriorityResponseDtoList(List<Priority> priorityList);

}
