package com.example.redunm.controller;

import com.example.redunm.model.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/signup/step4")
public class Step4Controller {

    @Autowired
    private UserService userService;

    @GetMapping
    public String signupStep4Form(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/signup/step1";
        }
        model.addAttribute("user", user);
        return "signupStep4";
    }

    @PostMapping
    public String signupStep4(@ModelAttribute("user") User user, Model model, HttpSession session) {

        // 전화번호 중복 확인
        if (userService.findByPhone(user.getPhone()).isPresent()) {
            model.addAttribute("error", "전화번호가 이미 사용 중입니다.");
            return "signupStep4";
        }

        // 세션에 전화번호 저장
        User sessionUser = (User) session.getAttribute("user");
        sessionUser.setPhone(user.getPhone());

        // 모든 정보 저장 완료 후, MongoDB에 저장
        userService.save(sessionUser);
        session.removeAttribute("user");
        return "redirect:/auth/login";
    }
}
