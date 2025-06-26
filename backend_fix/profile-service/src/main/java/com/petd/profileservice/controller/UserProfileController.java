package com.petd.profileservice.controller;

import com.petd.profileservice.dto.ApiResponse;
import com.petd.profileservice.dto.response.UserProfileResponse;
import com.petd.profileservice.service.UserProfileService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {

  UserProfileService userProfileService;

  @PostMapping("/users/internal")
   public ApiResponse<List<UserProfileResponse>> getProfiles(@RequestBody List<String> profileIds) {
      return ApiResponse.<List<UserProfileResponse> >builder()
          .result(userProfileService.getByUserIds(profileIds))
          .build();
  }
//
//  @GetMapping("/users")
//  ApiResponse<List<UserProfileResponse>> getAllProfiles() {
//    return ApiResponse.<List<UserProfileResponse>>builder()
//        .result(userProfileService.getAllProfiles())
//        .build();
//  }
}
