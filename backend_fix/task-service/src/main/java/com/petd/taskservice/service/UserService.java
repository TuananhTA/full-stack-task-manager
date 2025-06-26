package com.petd.taskservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petd.taskservice.dto.response.UserProfileResponse;
import com.petd.taskservice.exception.AppException;
import com.petd.taskservice.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

  ObjectMapper objectMapper;
  public UserProfileResponse getXUserLoginInfoFromHeader() {

    HttpServletRequest request = getCurrentHttpRequest();
    String userInfo = request.getHeader("X-User-Info");

    if (userInfo == null || userInfo.isBlank()) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
    try {
      return objectMapper.readValue(userInfo, UserProfileResponse.class);
    } catch (JsonProcessingException e) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
  }


  public HttpServletRequest getCurrentHttpRequest() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs != null) {
      return attrs.getRequest();
    } else {
      throw new IllegalStateException("No current request");
    }
  }
}
