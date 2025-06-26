package com.petd.profileservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
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
public class ProfileCreationRequest {

  @Email(message = "EMAIL_INVALID")
  @NotBlank(message = "EMAIL_REQUIRED")
  String email;

  @NotBlank(message = "PASSWORD_REQUIRED")
  String password;

  @NotBlank(message = "FIRST_NAME_REQUIRED")
  String firstName;

  @NotBlank(message = "LAST_NAME_REQUIRED")
  String lastName;

  @NotNull(message = "DOB_REQUIRED")
  @Past(message = "DOB_PAST")
  LocalDate dob;

  String departmentId;

}
