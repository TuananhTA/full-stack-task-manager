package com.petd.profileservice.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class UserProfileResponse {
  String id;
  String firstName;
  String lastName;
  LocalDate dob;
  LocalDateTime createdAt;
  String role;
  DepartmentResponse department;
}
