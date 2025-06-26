package com.petd.taskservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petd.taskservice.dto.response.MessageKafka;
import com.petd.taskservice.dto.response.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducerService {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  public void sendTaskEvent(Object data, String action) {
    try {

      MessageKafka<?> dataSender = MessageKafka.builder()
                                      .action(action)
                                      .data(data)
                                      .build();

      String json = objectMapper.writeValueAsString(dataSender);
      kafkaTemplate.send("task-event", json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
