package com.shunwei.oms.auth.config;

import com.shunwei.oms.auth.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    public SecurityConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // 禁用 CSRF 保护
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // 启用 CORS 支持
                .addFilterBefore(authenticationFilter, SecurityContextPersistenceFilter.class);  // 添加自定义过滤器
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");  // 允许所有来源
        config.addAllowedHeader("*");         // 允许所有请求头
        config.addAllowedMethod("*");         // 允许所有请求方法
        config.setAllowCredentials(true);     // 允许携带凭证（如 cookies）
        source.registerCorsConfiguration("/**", config);  // 应用到所有路径
        return source;
    }

}
