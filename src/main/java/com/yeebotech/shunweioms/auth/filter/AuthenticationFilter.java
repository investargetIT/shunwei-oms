package com.yeebotech.shunweioms.auth.filter;

import com.yeebotech.shunweioms.auth.dto.UserCheckResponse;
import com.yeebotech.shunweioms.auth.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private WebClient webClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getServletPath();
        // 放行 Swagger 和登录路径
        if (requestPath.startsWith("/swagger-ui/") || requestPath.startsWith("/v3/api-docs")
                || requestPath.startsWith("/swagger-resources/") || requestPath.startsWith("/webjars/")
                || requestPath.equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token == null || !validateToken(token)) {
            logger.warn("Invalid or missing token for request path: {}", request.getServletPath());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 设置403状态码
            return;
        }

        // 如果 token 有效，继续处理请求
        filterChain.doFilter(request, response);
        logger.info("Authorization header: {}", token);
    }

    private boolean validateToken(String token) {
        try {
            // 去除 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            UserCheckResponse userCheckResponse = webClient
                    .get()
                    .uri("/user/user-check?token=" + token)
                    .retrieve()
                    .bodyToMono(UserCheckResponse.class)
                    .block();

            // 根据返回的数据判断 token 是否有效
            return userCheckResponse != null
                    && userCheckResponse.getCode() == 200
                    && userCheckResponse.isSuccess()
                    && userCheckResponse.getData() != null
                    && userCheckResponse.getData().isHasLoginUser();
        } catch (Exception e) {
            logger.error("Token validation error", e);
            return false; // 如果出现任何异常，则认为 token 无效
        }
    }
}
