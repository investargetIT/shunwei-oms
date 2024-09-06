//package com.yeebotech.shunweioms.auth.config;
//
//import com.yeebotech.shunweioms.auth.filter.AuthenticationFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.context.SecurityContextPersistenceFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final AuthenticationFilter authenticationFilter;
//
//    public SecurityConfig(AuthenticationFilter authenticationFilter) {
//        this.authenticationFilter = authenticationFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 配置 CORS
//                .csrf(csrf -> csrf.disable())  // 禁用 CSRF 保护
//                .authorizeRequests()
//                .anyRequest().permitAll()  // 允许所有请求
//                .and()
//                .addFilterBefore(authenticationFilter, SecurityContextPersistenceFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);  // 必须设置为 true，如果客户端使用了 credentials
//        config.addAllowedOrigin("*");  // 允许所有来源
//        config.addAllowedHeader("*");  // 允许所有请求头
//        config.addAllowedMethod("*");  // 允许所有HTTP方法
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//
//}
