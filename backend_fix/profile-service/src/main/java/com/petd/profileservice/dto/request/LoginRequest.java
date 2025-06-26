package com.petd.profileservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

  @Email(message = "EMAIL_INVALID")
  @NotBlank(message = "EMAIL_REQUIRED")
  String email;

  @NotBlank(message = "PASSWORD_REQUIRED")
  String password;
}
