package com.example.redunm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())

                .cors(Customizer.withDefaults())

                // (3) 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 인증 없이 열어둘 경로를 명시합니다.
                        .requestMatchers(
                                "/api/auth/signup/**",  // REST API 회원가입
                                "/auth/signup/**",      // 혹시 /auth/signup/** 로도 쓰고 있다면
                                "/models/**",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // (4) 폼 로그인 기능 자체를 비활성화 (자동 리다이렉트X, 401 Unauthorized 응답)
                .formLogin(form -> form.disable())

                // (5) 로그아웃 설정 (필요 시)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}
