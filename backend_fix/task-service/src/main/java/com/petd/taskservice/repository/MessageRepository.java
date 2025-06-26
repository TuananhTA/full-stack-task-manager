package com.petd.taskservice.repository;

import com.petd.taskservice.entity.Message;
import com.petd.taskservice.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, String> {


  @Query("SELECT m from Message as m where m.toTask.id = :taskId ")
  List<Message> findByToTaskId(@Param("taskId") String taskId);
}
