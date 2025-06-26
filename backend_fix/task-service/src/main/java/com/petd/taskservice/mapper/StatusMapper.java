package com.petd.taskservice.mapper;

import com.petd.taskservice.dto.request.StatusRequestDto;
import com.petd.taskservice.dto.response.StatusResponseDto;
import com.petd.taskservice.entity.Status;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatusMapper {

  Status toStatus(StatusRequestDto statusRequestDto);

  StatusResponseDto toStatusResponseDto(Status status);

  List<StatusResponseDto> toStatusResponseDtoList(List<Status> status);

}
