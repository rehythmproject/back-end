package com.example.redunm.signup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/signup/success")
public class SuccessController {

    @GetMapping
    public ResponseEntity<?> success() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입이 성공적으로 완료되었습니다.");

        return ResponseEntity.ok(response);
    }
}
