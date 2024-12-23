package com.example.redunm.signup;

import com.example.redunm.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/signup/step2")
public class Step2Controller {

    @GetMapping
    public ResponseEntity<?> signupStep2Form(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Step1 과정을 먼저 진행해야 합니다.");
        }

        return ResponseEntity.ok(user);
    }


    @PostMapping
    public ResponseEntity<?> signupStep2(@RequestBody User user, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Step1 과정을 먼저 진행해야 합니다.");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("비밀번호가 일치하지 않습니다.");
        }

        sessionUser.setPassword(user.getPassword());
        sessionUser.setConfirmPassword(user.getConfirmPassword());
        session.setAttribute("user", sessionUser);

        return ResponseEntity.ok("회원가입 Step2 완료. Step3으로 이동 가능합니다.");
    }
}
