package com.petd.taskservice.service;

import com.petd.taskservice.dto.request.MessageRequest;
import com.petd.taskservice.dto.response.MessageResponse;
import com.petd.taskservice.entity.Message;
import com.petd.taskservice.entity.Task;
import com.petd.taskservice.exception.AppException;
import com.petd.taskservice.exception.ErrorCode;
import com.petd.taskservice.mapper.TaskMapper;
import com.petd.taskservice.repository.MessageRepository;
import com.petd.taskservice.repository.TaskRepository;
import com.petd.taskservice.repository.httpclient.ProfileClient;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.crypto.util.PQCOtherInfoGenerator.PartyU;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {

  MessageRepository messageRepository;
  TaskService taskService;
  ProfileClient profileClient;
  TaskMapper taskMapper;


  AssignmentBoardService assignmentBoardService;

  public MessageResponse pushAMessage(MessageRequest request) {
    System.out.println(request.getFromUser());
    System.out.println(request.getToTask());
    boolean isCheck = assignmentBoardService.checkAssignment(request.getToTask(), request.getFromUser().getId());
    if (!isCheck) throw new AppException(ErrorCode.ASS_NOT_EXIST);

    Task task = taskService.getTaskById(request.getToTask());

    Message message = new Message();
    message.setFromUser(request.getFromUser().getId());
    message.setToTask(task);
    message.setMessage(request.getText());
    messageRepository.save(message);


    MessageResponse response = new MessageResponse();
    response.setToTask(taskMapper.toTaskResponseDto(task));
    response.setFromUser(request.getFromUser());
    response.setCreatedAt(message.getCreatedAt());
    response.setText(request.getText());
    response.setId(message.getId());

    return response;
  }

  public List<MessageResponse> getAllMessagesByTask(String taskId) {
    List<Message> messages = messageRepository.findByToTaskId(taskId);

    return messages.stream()
        .map((item)->{
          MessageResponse response = new MessageResponse();
          response.setId(item.getId());
          response.setText(item.getMessage());
          response.setFromUser(profileClient.getUserProfile(item.getFromUser()));
          response.setToTask(taskMapper.toTaskResponseDto(item.getToTask()));
          response.setCreatedAt(item.getCreatedAt());
          return response;
        })
        .toList();
  }

}
