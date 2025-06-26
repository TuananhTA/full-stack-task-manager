package com.petd.gateway.config;

import com.petd.gateway.repository.ProfileClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {


  @Bean
  WebClient webClient() {
    return WebClient.builder()
        .baseUrl("http://localhost:8082/profile-manager")
        .build();
  }

  @Bean
  ProfileClient profileClient(WebClient webClient) {
    HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
        .builderFor(WebClientAdapter.create(webClient)).build();

    return httpServiceProxyFactory.createClient(ProfileClient.class);
  }
}
