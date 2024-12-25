package com.example.redunm.login;

import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import com.example.redunm.login.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController {

    @Autowired
    private UserService userService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // POST 요청
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpSession session) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (email == null || password == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("이메일과 비밀번호를 모두 입력해주세요.");
        }

        var optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("존재하지 않는 이메일입니다.");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("loggedInUser", user);

        return ResponseEntity.ok("로그인 성공");
    }

    // GET 요청에 대한 처리 추가
    @GetMapping
    public ResponseEntity<?> handleGetLogin() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("GET 메서드는 /api/auth/login 엔드포인트에서 지원되지 않습니다. POST 메서드를 사용하세요.");
    }

    // 로그아웃 처리 (POST 방식)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
