package com.example.redunm.login;

import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showLoginForm(Model model) {
        return "login";
    }
    @PostMapping
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        // 1. 사용자를 아이디로 검색
        var optionalUser = userService.findByUsername(username);

        // 2. 존재 여부 확인
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "존재하지 않는 아이디입니다.");
            return "login";
        }

        User user = optionalUser.get();


        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "login";
        }

        session.setAttribute("loggedInUser", user);

        return "redirect:/";
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/auth/login";
    }
}
