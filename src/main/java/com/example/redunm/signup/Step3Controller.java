package com.example.redunm.signup;

import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller 대신 @RestController 사용
@RequestMapping("/api/auth/signup/step3")
public class Step3Controller {

    private final UserService userService;

    private static final String SESSION_USER_KEY = "user"; // 세션 키 상수 정의

    public Step3Controller(UserService userService) {
        this.userService = userService;
    }

    // GET: Step 3 데이터 가져오기
    @GetMapping
    public ResponseEntity<?> getSignupStep3Data(HttpSession session) {
        User sessionUser = (User) session.getAttribute(SESSION_USER_KEY);
        if (sessionUser == null) {
            return ResponseEntity.status(400).body("Session expired. Please start from Step 1.");
        }

        return ResponseEntity.ok(sessionUser); // 세션에 저장된 사용자 정보 반환
    }

    // POST: Step 3 데이터 처리
    @PostMapping
    public ResponseEntity<?> processSignupStep3(
            @RequestBody User user, // 클라이언트에서 JSON으로 데이터 받음
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute(SESSION_USER_KEY);
        if (sessionUser == null) {
            return ResponseEntity.status(400).body("Session expired. Please start from Step 1.");
        }

        // 이메일 중복 확인
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("The email is already in use.");
        }

        // 세션에 이메일 저장
        sessionUser.setEmail(user.getEmail());
        session.setAttribute(SESSION_USER_KEY, sessionUser);

        return ResponseEntity.ok("Step 3 completed successfully. Proceed to Step 4.");
    }
}
