package com.petd.websocketservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petd.websocketservice.dto.response.TaskResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskCreation {

  ObjectMapper objectMapper;
  SimpMessagingTemplate messagingTemplate;

  @KafkaListener(topics = "task-event", groupId = "ws-group")
  public void listenCreate(String message) throws JsonProcessingException {
    JsonNode root = objectMapper.readTree(message);
    JsonNode dataNode = root.get("data");
    TaskResponseDto taskResponseDto = objectMapper.treeToValue(dataNode, TaskResponseDto.class);

    log.info("taskResponseDto: {}", message);


    messagingTemplate.convertAndSend("/room/project/" + "role/Manager/"  +  taskResponseDto.getProjectId(), message);
    messagingTemplate.convertAndSend("/room/project/" + "role/Admin/" +  taskResponseDto.getProjectId() , message);
  }

}
