package com.example.redunm.config;

import com.example.redunm.filter.JsonAuthenticationFilter;
import com.example.redunm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    // PasswordEncoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager Bean 정의
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // SecurityFilterChain Bean 정의
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        // CSRF 비활성화 및 CORS 설정
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 사용
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/signup/**",
                                "/api/auth/login/**",
                                "/api/cart/**",
                                "/api/data-models/**",
                                "/css/**",
                                "/js/**",
                                "/models/**"
                        ).permitAll() // 이 경로들은 인증 없이 접근 가능
                        .anyRequest().authenticated() // 나머지 경로들은 인증 필요
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                // 기본 formLogin 비활성화
                .formLogin(form -> form.disable());

        // 커스텀 JSON 인증 필터 추가
        JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter("/api/auth/login", authManager, userService);
        http.addFilterBefore(jsonAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
