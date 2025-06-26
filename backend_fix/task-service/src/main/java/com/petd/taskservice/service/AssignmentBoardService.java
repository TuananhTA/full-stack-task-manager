package com.petd.taskservice.service;


import com.petd.taskservice.dto.response.UserProfileResponse;
import com.petd.taskservice.entity.AssignmentBoard;
import com.petd.taskservice.entity.AssignmentBoardId;
import com.petd.taskservice.repository.AssignmentBoardRepository;
import com.petd.taskservice.repository.httpclient.ProfileClient;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignmentBoardService {

  AssignmentBoardRepository assignmentBoardRepository;
  ProfileClient profileClient;

  public boolean checkAssignment(String taskId, String userId) {


    AssignmentBoardId assignmentBoardId = new AssignmentBoardId(taskId, userId);
    AssignmentBoard assignmentBoard = assignmentBoardRepository.findById(assignmentBoardId)
        .orElse(null);
    return assignmentBoard != null;
  }

  public List<UserProfileResponse> assignedUsersByTask(String taskId) {
    List<String> assignedUserId = assignmentBoardRepository.findAllAssignedIdsByTaskId(taskId);
    return profileClient.getProfiles(assignedUserId);
  }
}
