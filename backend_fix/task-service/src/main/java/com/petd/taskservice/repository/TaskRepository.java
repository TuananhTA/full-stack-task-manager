package com.petd.taskservice.repository;

import com.petd.taskservice.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, String> {

  @Query(value = "Call get_task_by_user_and_project(:userId, :projectId, :role, :departmentId)", nativeQuery = true)
  List<Task> findInProject(
      @Param("userId") String userId,
      @Param("projectId") String projectId,
      @Param("role") String role,
      @Param("departmentId") String departmentId
  );


}
