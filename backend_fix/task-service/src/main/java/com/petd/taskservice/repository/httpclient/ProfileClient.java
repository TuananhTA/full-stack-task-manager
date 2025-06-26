package com.petd.taskservice.repository.httpclient;

import com.petd.taskservice.dto.request.ProfileRequest;
import com.petd.taskservice.dto.response.UserProfileResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "profile-service", url = "${app.services.profile}")
public interface ProfileClient {

    @GetMapping("/{profileId}")
    UserProfileResponse getUserProfile(@PathVariable String profileId);

    @PostMapping("/list-id")
    List<UserProfileResponse> getProfiles(@RequestBody List<String> profileIds);
}
