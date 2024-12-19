package com.example.redunm.signup;
import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/auth/signup/step3")
public class Step3Controller {
    @Autowired
    private UserService userService;
    @GetMapping
    public String signupStep3Form(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/signup/step1";
        }
        model.addAttribute("user", user);
        return "signupStep3";
    }
    @PostMapping
    public String signupStep3(@ModelAttribute("user") User user, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/auth/signup/step1";
        }
        // 이메일 중복 확인
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "이메일이 이미 사용 중입니다.");
            return "signupStep3";
        }
        sessionUser.setEmail(user.getEmail());
        session.setAttribute("user", sessionUser);
        return "redirect:/auth/signup/step4";
    }
}