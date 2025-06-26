package com.petd.websocketservice.service;

import com.petd.websocketservice.dto.request.ChatMessage;
import com.petd.websocketservice.dto.request.MessageRequest;
import com.petd.websocketservice.dto.response.ChatMessageResponse;
import com.petd.websocketservice.dto.response.MessageKafka;
import com.petd.websocketservice.dto.response.UserProfileResponse;
import com.petd.websocketservice.exception.AppException;
import com.petd.websocketservice.exception.ErrorCode;
import com.petd.websocketservice.repository.httpclient.ProfileClient;
import com.petd.websocketservice.repository.httpclient.TaskClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {

  ProfileClient profileClient;
  TaskClient taskClient;
  SimpMessagingTemplate messagingTemplate;

  public void handleMessage(ChatMessage message) {
    UserProfileResponse userProfileResponse = profileClient.getUserByToken("String");
    MessageRequest messageRequest = MessageRequest.builder()
        .text(message.getMessage())
        .fromUser(userProfileResponse)
        .toTask(message.getToTask())
        .build();
    try {
      ChatMessageResponse chatMessageResponse = taskClient.sendMessage(messageRequest);
      String taskId = message.getToTask();
      MessageKafka<ChatMessageResponse> kafkaMessage = MessageKafka.<ChatMessageResponse>builder()
          .action("NEW_MESSAGE")
          .data(chatMessageResponse)
          .build();

      messagingTemplate.convertAndSend("/room/task/" + taskId, kafkaMessage );
    }catch (AppException e) {
      throw new  AppException(ErrorCode.ASS_NOT_EXIST);
    }
  }
}
