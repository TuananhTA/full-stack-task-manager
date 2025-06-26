package com.petd.taskservice.repository;

import com.petd.taskservice.Projection.AssignmentBoardProjection;
import com.petd.taskservice.entity.AssignmentBoard;
import com.petd.taskservice.entity.AssignmentBoardId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignmentBoardRepository extends JpaRepository<AssignmentBoard, AssignmentBoardId> {

  @Query(value = "CALL get_assignment_board_by_task(:taskId)", nativeQuery = true)
  List<AssignmentBoardProjection> getAssignmentBoardByTaskId(@Param("taskId") String taskId);


  Optional<AssignmentBoard> findById(AssignmentBoardId id);

  @Query("SELECT ad.id.assigneeId from AssignmentBoard as ad where ad.id.taskId = :taskId")
  List<String> findAllAssignedIdsByTaskId(@Param("taskId") String taskId);

}
