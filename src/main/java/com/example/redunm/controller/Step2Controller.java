package com.example.redunm.controller;

import com.example.redunm.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller 대신 @RestController 사용
@RequestMapping("/api/auth/signup/step2")
public class Step2Controller {

    private static final String SESSION_USER_KEY = "user"; // 세션 키 상수

    // GET: Step 2 데이터 가져오기
    @GetMapping
    public ResponseEntity<?> getStep2Data(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_KEY);
        if (user == null) {
            return ResponseEntity.status(400).body("Session expired. Please start from Step 1.");
        }
        return ResponseEntity.ok(user); // 세션에 저장된 사용자 데이터를 JSON으로 반환
    }

    // POST: Step 2 데이터 처리
    @PostMapping
    public ResponseEntity<?> processStep2(@RequestBody User user, HttpSession session) {
        // 세션에서 기존 사용자 정보 가져오기
        User sessionUser = (User) session.getAttribute(SESSION_USER_KEY);
        if (sessionUser == null) {
            return ResponseEntity.status(400).body("Session expired. Please start from Step 1.");
        }

        // 비밀번호 확인 로직
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.status(400).body("Passwords do not match.");
        }

        // 세션 사용자 정보 업데이트
        sessionUser.setPassword(user.getPassword());
        sessionUser.setConfirmPassword(user.getConfirmPassword());
        session.setAttribute(SESSION_USER_KEY, sessionUser);

        // 성공적으로 처리된 경우
        return ResponseEntity.ok("Step 2 completed successfully. Proceed to Step 3.");
    }
}
