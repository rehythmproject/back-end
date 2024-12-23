package com.example.redunm.signup;

import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/signup/step4")
public class Step4Controller {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> signupStep4Form(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Step1 과정을 먼저 진행해야 합니다.");
        }
        return ResponseEntity.ok(user);
    }


    @PostMapping
    public ResponseEntity<?> signupStep4(@RequestBody User user, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Step1 과정을 먼저 진행해야 합니다.");
        }
        if (userService.findByPhone(user.getPhone()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("전화번호가 이미 사용 중입니다.");
        }

        sessionUser.setPhone(user.getPhone());

        userService.save(sessionUser);

        session.removeAttribute("user");

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}
