package com.petd.profileservice.service;

import com.petd.profileservice.dto.request.ProfileCreationRequest;
import com.petd.profileservice.dto.response.UserProfileResponse;
import com.petd.profileservice.entity.UserProfile;
import com.petd.profileservice.exception.AppException;
import com.petd.profileservice.exception.ErrorCode;
import com.petd.profileservice.mapper.UserProfileMapper;
import com.petd.profileservice.repository.UserProfileRepository;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {

  UserProfileRepository userProfileRepository;
  UserProfileMapper userProfileMapper;


  public UserProfile getProfile(String id) {
    return userProfileRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
  }

  public List<UserProfileResponse> getByUserIds(List<String> ids) {
    if (ids == null || ids.isEmpty()) {
      return Collections.emptyList();
    }
    List<UserProfile> userProfileList = userProfileRepository.findAllById(ids);
    return userProfileMapper.toUserProfileResponseList(userProfileList);
  }


}
