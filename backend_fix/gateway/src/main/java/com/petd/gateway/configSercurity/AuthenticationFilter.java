package com.petd.gateway.configSercurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petd.gateway.dto.ApiResponse;
import com.petd.gateway.dto.response.UserProfileResponse;
import com.petd.gateway.service.ProfileService;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class AuthenticationFilter  implements GlobalFilter, Ordered {

  ProfileService profileService;
  ObjectMapper mapper;

  String[] publicEndpoints = {
      "/profile-manager/account/login",
      "/profile-manager/account/register",
      "/profile-manager/account/create-admin"
  };

  @Value("${app.api-prefix}")
  @NonFinal
  private String apiPrefix;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    if(isPublicEndpoint(request)){
      return chain.filter(exchange);
    }

    // Check WebSocket
    boolean isWebSocket = "websocket".equalsIgnoreCase(request.getHeaders().getUpgrade());
    log.info("Incoming request: {} {} - WebSocket: {}", request.getMethod(), request.getURI(), isWebSocket);

    log.info("AuthenticationFilter");
    HttpCookie cookie = request.getCookies().getFirst("token");

    if(cookie == null){
        return unauthorizedResponse(response, 4001, "User must login");
    }
    String token = cookie.getValue();
    return profileService.getUserProfile(token)
        .flatMap(item -> {
          if(item.getCode() == 1000){
            exchange.getAttributes().put("userProfile", item.getResult());
            try {
              request.mutate()
                  .header("X-User-Info", mapper.writeValueAsString(item.getResult()))
                  .build();
            } catch (JsonProcessingException e) {
              return unauthorizedResponse(response, 5001, "Failed to fetch user profile");
            }
            return chain.filter(exchange);
          }
          return unauthorizedResponse(response, item.getCode(), item.getMessage());
        })
        .onErrorResume(WebClientResponseException.class, ex ->{
          String errorBody = new String(ex.getResponseBodyAsByteArray(), StandardCharsets.UTF_8);
          try {
            ApiResponse errorResponse = mapper.readValue(errorBody, ApiResponse.class);
            return unauthorizedResponse(response,errorResponse.getCode(), errorResponse.getMessage());
          } catch (JsonProcessingException e) {
            log.error("Error parsing error response: {}", errorBody, e);
            return unauthorizedResponse(response,5001, "Failed to fetch user profile");
          }
        });
  }
  @Override
  public int getOrder() {
    return -1;
  }

  private boolean isPublicEndpoint(ServerHttpRequest request ) {

    return Arrays.stream(publicEndpoints)
        .anyMatch(s -> request.getURI().getPath().matches(apiPrefix + s));
  }

  private Mono<Void> unauthorizedResponse(ServerHttpResponse response, int code, String message) {

    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code(code)
        .message(message)
        .build();

    try {
      ObjectMapper mapper = new ObjectMapper();
      byte[] responseBytes = mapper.writeValueAsBytes(apiResponse);
      DataBuffer buffer = response.bufferFactory().wrap(responseBytes);
      return response.writeWith(Mono.just(buffer));
    } catch (Exception e) {
      return response.setComplete();
    }
  }
}
