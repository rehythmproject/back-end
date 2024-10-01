package com.example.redunm.controller;

import com.example.redunm.model.User;
import com.example.redunm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller // RestController에서 Controller로 변경
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup") // 회원가입 페이지로 이동
    public String signupForm(Model model) {
        model.addAttribute("user", new User()); // 폼에서 사용할 빈 User 객체 추가
        return "signup"; // templates 폴더의 signup.html로 연결
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username is already taken");
            return "signup"; // 오류 메시지를 가진 채로 다시 signup 페이지로 이동
        }

        userService.save(user);
        model.addAttribute("message", "User registered successfully");
        return "login"; // 회원가입 후 로그인 페이지로 이동
    }

    @GetMapping("/login") // 로그인 페이지로 이동
    public String loginForm(Model model) {
        model.addAttribute("user", new User()); // 폼에서 사용할 빈 User 객체 추가
        return "login"; // templates 폴더의 login.html로 연결
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) {
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent() &&
                existingUser.get().getPassword().equals(user.getPassword())) {
            model.addAttribute("message", "Login successful");
            return "welcome"; // 로그인 성공 후 welcome 페이지로 이동 (추가할 수 있음)
        }
        model.addAttribute("error", "Invalid credentials");
        return "login"; // 로그인 실패 시 로그인 페이지로 다시 이동
    }
}
