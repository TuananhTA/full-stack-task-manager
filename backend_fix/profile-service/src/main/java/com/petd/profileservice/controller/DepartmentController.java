package com.petd.profileservice.controller;


import com.petd.profileservice.dto.ApiResponse;
import com.petd.profileservice.dto.request.DepartmentRequest;
import com.petd.profileservice.dto.response.DepartmentResponse;
import com.petd.profileservice.entity.Department;
import com.petd.profileservice.service.DepartmentService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {

  DepartmentService departmentService;

  @PostMapping
  public ApiResponse<DepartmentResponse> create(@RequestBody DepartmentRequest request) {
    return ApiResponse.<DepartmentResponse>builder()
        .result(departmentService.create(request))
        .build();
  }
  @GetMapping
  public ApiResponse<List<DepartmentResponse>> getAll() {
    return ApiResponse.<List<DepartmentResponse>>builder()
        .result(departmentService.getAll())
        .build();
  }

}
