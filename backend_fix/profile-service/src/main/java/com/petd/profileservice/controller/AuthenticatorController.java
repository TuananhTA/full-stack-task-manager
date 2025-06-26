package com.petd.profileservice.controller;

import com.petd.profileservice.dto.ApiResponse;
import com.petd.profileservice.dto.request.LoginRequest;
import com.petd.profileservice.dto.request.ProfileCreationRequest;
import com.petd.profileservice.dto.response.UserProfileResponse;
import com.petd.profileservice.service.AccountService;
import com.petd.profileservice.service.RequestService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticatorController {

  AccountService accountService;
  RequestService requestService;

  @PostMapping("/register")
  public ApiResponse<UserProfileResponse> register(
      @RequestBody @Valid ProfileCreationRequest request
  ) {
    return ApiResponse.<UserProfileResponse>builder()
        .result(accountService.register(request))
        .build();
  }

  @PostMapping("login")
  public ApiResponse<UserProfileResponse> login(
      @RequestBody @Valid LoginRequest loginRequest,
      HttpServletResponse response
  ) throws Exception {
    return ApiResponse.<UserProfileResponse>builder()
        .result(accountService.login(loginRequest,response))
        .build();
  }

  @GetMapping("/get-user-by-token")
  public ApiResponse<UserProfileResponse> getUserByToken(
      @RequestParam("token") String token
  ){
    return ApiResponse.<UserProfileResponse>builder()
        .result(accountService.getProfileByToken(token))
        .build();
  }
  @GetMapping("/me")
  public ApiResponse<UserProfileResponse> me(HttpServletRequest request) {
    UserProfileResponse response = requestService.getXUserLoginInfoFromHeader();
    return ApiResponse.<UserProfileResponse>builder()
        .result(response)
        .build();
  }

  @PostMapping("/create-admin")
  public ApiResponse<UserProfileResponse> createAdmin(
      @RequestBody @Valid ProfileCreationRequest request
  ) {
    return ApiResponse.<UserProfileResponse>builder()
        .result(accountService.creatAdmin(request))
        .build();
  }
}
