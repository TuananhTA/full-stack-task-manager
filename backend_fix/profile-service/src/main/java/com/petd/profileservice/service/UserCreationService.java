package com.petd.profileservice.service;

import com.petd.profileservice.customSercurity.PasswordUtil;
import com.petd.profileservice.dto.request.ProfileCreationRequest;
import com.petd.profileservice.dto.response.UserProfileResponse;
import com.petd.profileservice.entity.Account;
import com.petd.profileservice.entity.Department;
import com.petd.profileservice.entity.UserProfile;
import com.petd.profileservice.exception.AppException;
import com.petd.profileservice.exception.ErrorCode;
import com.petd.profileservice.mapper.UserProfileMapper;
import com.petd.profileservice.repository.AccountRepository;
import com.petd.profileservice.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserCreationService {


  AccountRepository accountRepository;
  UserProfileRepository userProfileRepository;
  UserProfileMapper userProfileMapper;
  DepartmentService departmentService;

  @Transactional
  public UserProfileResponse createUser(ProfileCreationRequest request, String role){
    Department department = departmentService.getByIdNoEx(request.getDepartmentId());

    try {
      Account account = new Account();
      account.setActive(true);
      account.setEmail(request.getEmail());
      account.setPassword(PasswordUtil.hashPassword(request.getPassword()));
      account.setRole(role);
      accountRepository.save(account);
      accountRepository.flush();

      UserProfile userProfile = new UserProfile();
      userProfile.setAccount(account);
      userProfile.setFirstName(request.getFirstName());
      userProfile.setLastName(request.getLastName());
      userProfile.setDob(request.getDob());
      userProfile.setDepartment(department);
      userProfileRepository.save(userProfile);

      return userProfileMapper.toUserProfileResponse(userProfile);
    } catch (DataIntegrityViolationException e) {
      throw new AppException(ErrorCode.EMAIL_EXISTED);
    }
  }
}
