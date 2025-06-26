package com.petd.taskservice.repository;

import com.petd.taskservice.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, String> {

}
