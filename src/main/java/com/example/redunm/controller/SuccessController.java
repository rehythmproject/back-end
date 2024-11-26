package com.example.redunm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/signup/success")
public class SuccessController {

    @GetMapping
    public ResponseEntity<?> success() {
        // 성공 메시지를 JSON 형태로 반환
        return ResponseEntity.ok("Signup successful");
    }
}
