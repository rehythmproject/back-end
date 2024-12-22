package com.example.redunm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity                  // 웹 보안 활성화
@EnableMethodSecurity              // 메서드 보안(@PreAuthorize 등) 활성화 (선택)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 요청 경로별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/models/**", "/auth/**").permitAll() // 인증 없이 접근 가능
                        .anyRequest().authenticated()                          // 그 외는 인증 필요
                )

                // 기본 로그인 페이지 사용
                .formLogin(Customizer.withDefaults())

                // 로그아웃 관련 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 경로
                        .permitAll()
                );

        return http.build();
    }
}
