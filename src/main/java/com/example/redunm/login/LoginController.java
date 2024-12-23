package com.example.redunm.login;

import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody,
                                   HttpSession session) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        var optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("존재하지 않는 아이디입니다.");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("loggedInUser", user);

        return ResponseEntity.ok("로그인 성공");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
