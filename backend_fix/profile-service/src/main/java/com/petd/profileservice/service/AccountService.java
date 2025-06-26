package com.petd.profileservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petd.profileservice.customSercurity.JwtUtils;
import com.petd.profileservice.customSercurity.PasswordUtil;
import com.petd.profileservice.dto.request.LoginRequest;
import com.petd.profileservice.dto.request.ProfileCreationRequest;
import com.petd.profileservice.dto.response.UserProfileResponse;
import com.petd.profileservice.entity.Account;
import com.petd.profileservice.entity.UserProfile;
import com.petd.profileservice.exception.AppException;
import com.petd.profileservice.exception.ErrorCode;
import com.petd.profileservice.mapper.UserProfileMapper;
import com.petd.profileservice.repository.AccountRepository;
import com.petd.profileservice.repository.UserProfileRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountService {

  AccountRepository accountRepository;
  UserProfileRepository userProfileRepository;
  UserProfileMapper userProfileMapper;
  UserCreationService userCreationService;
  RequestService requestService;
  JwtUtils jwtUtils;



  public UserProfileResponse register(ProfileCreationRequest request) {
    return userCreationService.createUser(request,"role/Employee");
  }

  public UserProfileResponse creatAdmin(ProfileCreationRequest request) {
    return userCreationService.createUser(request, "role/Admin");
  }
  public UserProfileResponse login (
      LoginRequest loginRequest,
      HttpServletResponse httpResponse
  )
      throws Exception {

    Account account = accountRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
    boolean isMatchingPassword = PasswordUtil.verifyPassword(
        loginRequest.getPassword(),
        account.getPassword()
    );
    if (!isMatchingPassword) {
      throw new AppException(ErrorCode.ACCOUNT_NOT_EXISTED);
    }
    if(!account.isActive()){
      throw new AppException(ErrorCode.ACCOUNT_IS_LOCKED);
    }

    String token = jwtUtils.generateToken(account.getEmail());

    Cookie cookie = new Cookie("token", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge((int) jwtUtils.getExpiration() / 1000);

    httpResponse.addCookie(cookie);
    return userProfileMapper.toUserProfileResponse(account.getUserProfile());

  }

  public UserProfileResponse getProfileByToken (String token) {
    String username = jwtUtils.getUsernameFromToken(token);
    Account account = accountRepository.findByEmail(username)
        .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
    return userProfileMapper.toUserProfileResponse(account.getUserProfile());
  }
}
