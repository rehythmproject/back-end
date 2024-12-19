package com.example.redunm.signup;
import com.example.redunm.entity.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/signup/step1")

public class Step1Controller {

    @Autowired
    private UserService userService;

    @GetMapping
    public String signupForm(Model model) {
        model.addAttribute("user", new User()); // 폼에서 사용할 빈 User 객체 추가
        return "signupStep1"; // templates 폴더의 signup.html로 연결
    }

    @PostMapping
    public String signupStep1(@ModelAttribute("user") User user, Model model, HttpSession session) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "이미 존재하는 아이디 입니다.");
            return "signupStep1";
        }

        session.setAttribute("user", user);
        return "redirect:/auth/signup/step2";
    }
}