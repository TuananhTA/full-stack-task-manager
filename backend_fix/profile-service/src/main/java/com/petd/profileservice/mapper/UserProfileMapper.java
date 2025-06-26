package com.petd.profileservice.mapper;

import com.petd.profileservice.dto.request.ProfileCreationRequest;
import com.petd.profileservice.dto.response.UserProfileResponse;
import com.petd.profileservice.entity.UserProfile;
import com.petd.profileservice.helper.ProfileMapperHelper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfileMapperHelper.class})
public interface UserProfileMapper {

  @Mapping(target = "department", source = "departmentId", qualifiedByName = "mapDepartment")
  UserProfile toUserProfile(ProfileCreationRequest request);

  @Mapping(target = "role", source = "account", qualifiedByName = "mapRole")
  @Mapping(target = "department", source = "department", qualifiedByName = "mapDepartment")
  UserProfileResponse toUserProfileResponse(UserProfile entity);

  List<UserProfileResponse> toUserProfileResponseList(List<UserProfile> entities);


}
