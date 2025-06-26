package com.petd.taskservice.repository;

import com.petd.taskservice.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, String> {

  @Query(value = "CALL get_projects_by_user(:userId)", nativeQuery = true)
  List<Project> findByProjectUserId(@Param("userId") String userId);

  @Query(value = "select * from project where project.department_id = :departmentId", nativeQuery = true)
  List<Project> findByDepartmentId(@Param("departmentId") String departmentId);
}
