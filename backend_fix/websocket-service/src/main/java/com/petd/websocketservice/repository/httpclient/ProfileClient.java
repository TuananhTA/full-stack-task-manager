package com.petd.websocketservice.repository.httpclient;

import com.petd.websocketservice.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "profile-service", url = "${app.services.profile}")
public interface ProfileClient {

    @GetMapping("/user-by-token")
    UserProfileResponse getUserByToken(@RequestParam("token") String token) ;
}
