package com.example.redunm.controller;

import com.example.redunm.model.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller 대신 @RestController 사용
@RequestMapping("/api/auth/signup/step1")
public class Step1Controller {

    private final UserService userService;

    private static final String SESSION_USER_KEY = "user"; // 세션 키 상수 정의

    public Step1Controller(UserService userService) {
        this.userService = userService;
    }

    // GET: Step 1 데이터 가져오기
    @GetMapping
    public ResponseEntity<?> getSignupForm(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_KEY);
        if (user == null) {
            // 세션에 사용자 정보가 없으면 새 객체 반환
            return ResponseEntity.ok(new User());
        }
        // 세션에 이미 사용자 데이터가 있는 경우 반환
        return ResponseEntity.ok(user);
    }

    // POST: Step 1 데이터 처리
    @PostMapping
    public ResponseEntity<?> processSignupStep1(
            @RequestBody User user, // 클라이언트에서 JSON 형태로 데이터를 받음
            HttpSession session
    ) {
        // 아이디 중복 확인
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body("이미 존재하는 아이디입니다."); // 400 Bad Request와 메시지 반환
        }

        // 세션에 사용자 정보 저장
        session.setAttribute(SESSION_USER_KEY, user);

        // 성공적으로 처리된 경우
        return ResponseEntity.ok("Step 1 completed successfully. Proceed to Step 2.");
    }
}
