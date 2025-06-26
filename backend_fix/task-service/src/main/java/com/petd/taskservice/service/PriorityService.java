package com.petd.taskservice.service;

import com.petd.taskservice.dto.request.PriorityRequestDto;
import com.petd.taskservice.dto.response.PriorityResponseDto;
import com.petd.taskservice.entity.Priority;
import com.petd.taskservice.entity.Status;
import com.petd.taskservice.exception.AppException;
import com.petd.taskservice.exception.ErrorCode;
import com.petd.taskservice.mapper.PriorityMapper;
import com.petd.taskservice.repository.PriorityRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriorityService {

  PriorityRepository priorityRepository;
  PriorityMapper priorityMapper;


  public PriorityResponseDto create(PriorityRequestDto priorityRequestDto) {

    Priority priority = priorityMapper.toPriority(priorityRequestDto);

    try{
      priorityRepository.save(priority);
    }catch (DataIntegrityViolationException e){
      throw new AppException(ErrorCode.PRIORITY_NAME_EXISTED);
    }
    return priorityMapper.toPriorityResponseDto(priority);

  }

  public List<PriorityResponseDto> getAll() {
    List<Priority> priorities = priorityRepository.findAll();
    return priorityMapper.toPriorityResponseDtoList(priorities);
  }

  public Priority getById(String id) {
    return priorityRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_STATUS));
  }

}
