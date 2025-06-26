package com.petd.taskservice.dto.request;

import com.petd.taskservice.dto.response.UserProfileResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRequest {
  UserProfileResponse fromUser;
  String toTask;
  String text;
}
