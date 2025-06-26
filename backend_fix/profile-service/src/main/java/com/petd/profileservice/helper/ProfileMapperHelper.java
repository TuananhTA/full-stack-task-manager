package com.petd.profileservice.helper;


import com.petd.profileservice.dto.response.DepartmentResponse;
import com.petd.profileservice.entity.Account;
import com.petd.profileservice.entity.Department;
import com.petd.profileservice.exception.AppException;
import com.petd.profileservice.exception.ErrorCode;
import com.petd.profileservice.mapper.DepartmentMapper;
import com.petd.profileservice.repository.DepartmentRepository;
import com.petd.profileservice.service.DepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Named;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileMapperHelper {

  DepartmentRepository departmentRepository;
  DepartmentMapper departmentMapper;


  @Named("mapRole")
  public String mapRole(Account account) {
    return account.getRole();
  }

  @Named("mapDepartment")
  public Department mapDepartment(String  id) {
    if (id == null || id.isBlank())
      return null;
    return departmentRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOTFOUND));
  }
  @Named("mapDepartment")
  public DepartmentResponse mapDepartment(Department department) {
    if(department == null) return null;
    return departmentMapper.toDepartmentResponse(department);
  }
}
