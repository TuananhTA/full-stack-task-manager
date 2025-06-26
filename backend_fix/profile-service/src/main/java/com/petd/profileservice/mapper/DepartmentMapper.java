package com.petd.profileservice.mapper;

import com.petd.profileservice.dto.request.DepartmentRequest;
import com.petd.profileservice.dto.response.DepartmentResponse;
import com.petd.profileservice.entity.Department;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

  Department toDepartment(DepartmentRequest departmentRequest);

  DepartmentResponse toDepartmentResponse(Department department);


  List<DepartmentResponse> toDepartmentResponseList(List<Department> departmentList);
}
