package com.petd.profileservice.service;

import com.petd.profileservice.dto.request.DepartmentRequest;
import com.petd.profileservice.dto.response.DepartmentResponse;
import com.petd.profileservice.dto.response.UserProfileResponse;
import com.petd.profileservice.entity.Department;
import com.petd.profileservice.entity.UserProfile;
import com.petd.profileservice.exception.AppException;
import com.petd.profileservice.exception.ErrorCode;
import com.petd.profileservice.mapper.DepartmentMapper;
import com.petd.profileservice.repository.DepartmentRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentService {

  DepartmentRepository departmentRepository;
  RequestService requestService;
  UserProfileService userProfileService;
  DepartmentMapper departmentMapper;

  public DepartmentResponse create(DepartmentRequest request) {

    if(!isAdmin()) {
      throw new AppException(ErrorCode.FORBIDDEN_ACTION);
    }
    Department department = departmentMapper.toDepartment(request);
    departmentRepository.save(department);
    return departmentMapper.toDepartmentResponse(department);
  }

  public List<DepartmentResponse> getAll() {

    UserProfileResponse userProfileResponse = requestService.getXUserLoginInfoFromHeader();
    String role = userProfileResponse.getRole();

    List<Department> departments = new ArrayList<>();

    if(role.equals("role/Admin")) {
      departments = departmentRepository.findAll();
    }else {
      UserProfile userProfile = userProfileService.getProfile(userProfileResponse.getId());
      if(userProfile.getDepartment() != null) {
        departments = List.of(userProfile.getDepartment());
      }
    }
    return departmentMapper.toDepartmentResponseList(departments);
  }


  private boolean isAdmin (){
    UserProfileResponse userProfileResponse = requestService.getXUserLoginInfoFromHeader();
    String role = userProfileResponse.getRole();
    return role.equals("role/Admin");
  }

  public Department getByIdNoEx(String id) {
    if (id == null || id.isBlank())
      return null;
    return departmentRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOTFOUND));
  }
}
