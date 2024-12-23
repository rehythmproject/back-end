package com.example.redunm.signup;

import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/signup/step1")
public class Step1Controller {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> signupForm() {

        User emptyUser = new User();
        return ResponseEntity.ok(emptyUser);
    }

    @PostMapping
    public ResponseEntity<?> signupStep1(@RequestBody User user, HttpSession session) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("이미 존재하는 아이디 입니다.");
        }

        session.setAttribute("user", user);


        return ResponseEntity.ok("회원가입 Step1 완료");
    }
}
