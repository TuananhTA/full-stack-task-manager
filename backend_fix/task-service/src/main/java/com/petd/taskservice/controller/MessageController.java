package com.petd.taskservice.controller;


import com.petd.taskservice.dto.request.MessageRequest;
import com.petd.taskservice.dto.response.ApiResponse;
import com.petd.taskservice.dto.response.MessageResponse;
import com.petd.taskservice.entity.Message;
import com.petd.taskservice.service.MessageService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {


  MessageService messageService;


  @PostMapping
  public MessageResponse sendMessage(@RequestBody MessageRequest request) {
    return messageService.pushAMessage(request);
  }

  @GetMapping("/{taskId}")
  public ApiResponse<List<MessageResponse>> getMessage(@PathVariable String taskId) {
    return ApiResponse.<List<MessageResponse>>builder()
        .result(messageService.getAllMessagesByTask(taskId))
        .build();
  }
}
