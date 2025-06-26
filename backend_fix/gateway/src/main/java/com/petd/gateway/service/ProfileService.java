package com.petd.gateway.service;

import com.petd.gateway.dto.ApiResponse;
import com.petd.gateway.dto.response.UserProfileResponse;
import com.petd.gateway.repository.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileService {

  @Lazy
  ProfileClient profileClient;

  public Mono<ApiResponse<UserProfileResponse>> getUserProfile(String token) {
    return profileClient.getUserProfile(token);
  }

}
