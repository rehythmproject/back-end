package com.example.redunm.signup;

import com.example.redunm.entity.User;
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
            return "redirect:/auth/signup/step1"; // 세션이 없으면 Step1로 리다이렉트
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

        // 세션에 저장된 사용자 정보 업데이트
        User sessionUser = (User) session.getAttribute("user");
        sessionUser.setPhone(user.getPhone());

        // 사용자 정보 MongoDB에 저장
        userService.save(sessionUser);

        // 세션에서 사용자 정보 제거
        session.removeAttribute("user");

        // 회원가입 성공 페이지로 리다이렉트
        return "redirect:/auth/signup/success";
    }
}
