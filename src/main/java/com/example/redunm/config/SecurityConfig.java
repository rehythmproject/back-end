package com.example.redunm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
                .cors(Customizer.withDefaults())        // 기본 CORS 설정

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/signup/**",  // 회원가입 관련 요청 허용
                                "/api/auth/login/**",   // 로그인 관련 요청 허용
                                "/api/cart/**",         // 장바구니 API 요청 허용
                                "/api/data-models/**",  // 데이터 모델 API 요청 허용
                                "/css/**",             // 정적 리소스 허용
                                "/js/**",
                                "/models/**"
                        ).permitAll()
                        .anyRequest().authenticated()  // 그 외의 요청은 인증 필요
                )

                .formLogin(form -> form
                        .loginPage("/login")             // 커스텀 로그인 페이지
                        .loginProcessingUrl("/api/auth/login") // 로그인 요청 URL
                        .defaultSuccessUrl("/home", true) // 로그인 성공 후 이동 URL
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")   // 로그아웃 URL
                        .logoutSuccessUrl("/")           // 로그아웃 성공 후 이동 URL
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
