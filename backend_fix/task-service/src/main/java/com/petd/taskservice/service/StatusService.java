package com.petd.taskservice.service;

import com.petd.taskservice.dto.request.StatusRequestDto;
import com.petd.taskservice.dto.response.StatusResponseDto;
import com.petd.taskservice.entity.Status;
import com.petd.taskservice.exception.AppException;
import com.petd.taskservice.exception.ErrorCode;
import com.petd.taskservice.mapper.StatusMapper;
import com.petd.taskservice.repository.StatusRepository;
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
public class StatusService {

   StatusRepository statusRepository;
   StatusMapper statusMapper;

   public StatusResponseDto create(StatusRequestDto requestDto) {
     log.info("Create status request: {}", requestDto.getColor());
     Status status = statusMapper.toStatus(requestDto);
     log.info("Create status: {}", status.getColor());
     try {
        statusRepository.save(status);
     }catch (DataIntegrityViolationException dataE){
       throw new AppException(ErrorCode.STATUS_NAME_EXISTED);
     }
     return statusMapper.toStatusResponseDto(status);
   }
   public List<StatusResponseDto> getAll() {
     List<Status> statuses = statusRepository.findAll();
     return statusMapper.toStatusResponseDtoList(statuses);
   }

   public Status getById(String id) {
     return statusRepository.findById(id)
         .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_STATUS));
   }

}
