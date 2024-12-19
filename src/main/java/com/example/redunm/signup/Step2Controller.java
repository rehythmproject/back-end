package com.example.redunm.signup;
import com.example.redunm.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/signup/step2")
public class Step2Controller {

    @GetMapping
    public String signupStep2Form(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/signup/step1"; // 세션에 데이터가 없으면 첫 단계로 이동
        }
        model.addAttribute("user", user);
        return "signupStep2"; // 두 번째 단계 폼
    }

    @PostMapping
    public String signupStep2(@ModelAttribute("user") User user, Model model, HttpSession session) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "signupStep2";
        }
        // 세션에 비밀번호 저장하는 부분
        User sessionUser = (User) session.getAttribute("user");
        sessionUser.setPassword(user.getPassword());
        sessionUser.setConfirmPassword(user.getConfirmPassword());
        session.setAttribute("user", sessionUser);
        return "redirect:/auth/signup/step3";
    }
}