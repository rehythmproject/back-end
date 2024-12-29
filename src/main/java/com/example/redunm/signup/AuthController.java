package com.example.redunm.signup;

import com.example.redunm.dto.SignUpRequest;
import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 엔드포인트
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("비밀번호가 일치하지 않습니다.");
        }

        if (userService.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("이미 존재하는 아이디 입니다.");
        }

        if (userService.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("이메일이 이미 사용 중입니다.");
        }

        if (userService.findByPhone(signUpRequest.getPhone()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("전화번호가 이미 사용 중입니다.");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword()); // PasswordEncoder는 UserService.save()에서 처리됨
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());

        userService.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입이 성공적으로 완료되었습니다.");

        return ResponseEntity.ok(response);
    }

    // 로그아웃 엔드포인트 (Spring Security에서 처리)
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // 로그아웃은 SecurityConfig에서 처리되므로, 별도의 로직이 필요 없습니다.
        Map<String, String> response = new HashMap<>();
        response.put("message", "로그아웃이 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(response);
    }
}
