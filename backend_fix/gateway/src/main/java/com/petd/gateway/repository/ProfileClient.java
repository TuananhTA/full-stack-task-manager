package com.petd.gateway.repository;

import com.petd.gateway.dto.ApiResponse;
import com.petd.gateway.dto.response.UserProfileResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;


public interface ProfileClient {

    @GetExchange(url = "/account/get-user-by-token")
    Mono<ApiResponse<UserProfileResponse>> getUserProfile(@RequestParam(name = "token") String token);

}
