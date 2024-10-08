package com.shunwei.oms.auth.service;

import com.shunwei.oms.auth.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.naming.AuthenticationException;

@Service
public class AuthService {

    private final WebClient webClient;

    @Autowired
    public AuthService(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserInfo verifyToken(String token) throws AuthenticationException {
        try {
            return webClient.get()
                    .uri("/auth/verify")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(UserInfo.class)
                    .block(); // 阻塞操作
        } catch (WebClientResponseException e) {
            throw new AuthenticationException("Token verification failed");
        }
    }
}
