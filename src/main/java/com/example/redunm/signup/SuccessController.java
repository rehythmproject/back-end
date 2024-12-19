package com.example.redunm.signup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/signup/success")
public class SuccessController {
    @GetMapping
    public String success(Model model) {
        model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
        return "signupSuccess"; // 회원가입 성공 뷰로 이동
    }
}
