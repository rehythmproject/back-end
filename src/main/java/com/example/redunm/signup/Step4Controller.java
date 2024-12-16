package com.example.redunm.signup;

import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller 대신 @RestController 사용
@RequestMapping("/api/auth/signup/step4")
public class Step4Controller {

    private final UserService userService;

    private static final String SESSION_USER_KEY = "user"; // 세션 키 상수 정의

    public Step4Controller(UserService userService) {
        this.userService = userService;
    }

    // GET: Step 4 데이터 가져오기
    @GetMapping
    public ResponseEntity<?> getSignupStep4Data(HttpSession session) {
        User sessionUser = (User) session.getAttribute(SESSION_USER_KEY);
        if (sessionUser == null) {
            return ResponseEntity.status(400).body("Session expired. Please start from Step 1.");
        }
//일단 주석 처리 한다
        return ResponseEntity.ok(sessionUser); // 세션에 저장된 사용자 정보 반환
    }

    // POST: Step 4 데이터 처리
    @PostMapping
    public ResponseEntity<?> processSignupStep4(
            @RequestBody User user, // 클라이언트에서 JSON으로 데이터 받음
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute(SESSION_USER_KEY);
        if (sessionUser == null) {
            return ResponseEntity.status(400).body("Session expired. Please start from Step 1.");
        }

        // 전화번호 중복 확인
        if (userService.findByPhone(user.getPhone()).isPresent()) {
            return ResponseEntity.status(400).body("This phone number is already in use.");
        }

        // 세션에 전화번호 저장
        sessionUser.setPhone(user.getPhone());

        // 사용자 정보 MongoDB에 저장
        userService.save(sessionUser);

        // 세션 정리
        session.removeAttribute(SESSION_USER_KEY);

        return ResponseEntity.ok("Signup completed successfully.");
    }
}
