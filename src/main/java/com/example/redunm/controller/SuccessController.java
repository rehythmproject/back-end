package com.example.redunm.controller;

import com.example.redunm.model.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/signup/success")
public class SuccessController {

    @GetMapping
    public String success(Model model){
        model.addAttribute("message", "회원가입이 성공적으로 완료 되었습니다.");
        return "signupSuccess";
    }
}
