package com.petd.taskservice.controller;

import com.petd.taskservice.dto.request.PriorityRequestDto;
import com.petd.taskservice.dto.response.ApiResponse;
import com.petd.taskservice.dto.response.PriorityResponseDto;
import com.petd.taskservice.service.PriorityService;
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
@RequestMapping("/priority")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriorityControllerApi {


  PriorityService priorityService;

  @PostMapping
  public ApiResponse<PriorityResponseDto> create(@RequestBody @Valid PriorityRequestDto requestDto) {
    return ApiResponse.<PriorityResponseDto>builder()
        .result(priorityService.create(requestDto))
        .build();
  }

  @GetMapping
  public ApiResponse<List<PriorityResponseDto>> getStatuses() {
    return ApiResponse.<List<PriorityResponseDto>>builder()
        .result(priorityService.getAll())
        .build();
  }

}
