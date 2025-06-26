package com.petd.websocketservice.controller;

import com.petd.websocketservice.dto.request.ChatMessage;
import com.petd.websocketservice.service.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandleController {

  MessageService messageService;


  @MessageMapping("/chat.task")
  public void sendPrivateMessage(ChatMessage message) {
    System.out.println("Sending Private Message");
    messageService.handleMessage(message);
  }
}
