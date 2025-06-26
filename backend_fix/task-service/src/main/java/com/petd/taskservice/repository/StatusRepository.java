package com.petd.taskservice.repository;

import com.petd.taskservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, String> {

}
