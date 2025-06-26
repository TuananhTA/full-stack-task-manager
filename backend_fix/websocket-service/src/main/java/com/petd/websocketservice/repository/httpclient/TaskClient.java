package com.petd.websocketservice.repository.httpclient;

import com.petd.websocketservice.dto.request.MessageRequest;
import com.petd.websocketservice.dto.response.ChatMessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "task-service", url = "${app.services.task}")
public interface TaskClient {

  @PostMapping("/message")
  ChatMessageResponse sendMessage(@RequestBody MessageRequest request) ;
}
