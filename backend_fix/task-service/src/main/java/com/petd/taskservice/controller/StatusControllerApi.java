package com.petd.taskservice.controller;

import com.petd.taskservice.dto.request.StatusRequestDto;
import com.petd.taskservice.dto.response.ApiResponse;
import com.petd.taskservice.dto.response.StatusResponseDto;
import com.petd.taskservice.entity.Status;
import com.petd.taskservice.service.StatusService;
import jakarta.validation.Valid;
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
@RequestMapping("/status")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatusControllerApi {


  StatusService statusService;

  @PostMapping
  public ApiResponse<StatusResponseDto> create(@RequestBody @Valid StatusRequestDto requestDto) {
    return ApiResponse.<StatusResponseDto>builder()
        .result(statusService.create(requestDto))
        .build();
  }
  @GetMapping
  public ApiResponse<List<StatusResponseDto>> getStatuses() {
    return ApiResponse.<List<StatusResponseDto>>builder()
        .result(statusService.getAll())
        .build();
  }

}
