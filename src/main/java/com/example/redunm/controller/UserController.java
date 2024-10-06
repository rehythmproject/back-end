package com.example.redunm.controller;

import com.example.redunm.model.User;
import com.example.redunm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller // RestController에서 Controller로 변경
@SessionAttributes("user")
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup/step1") // 회원가입 페이지로 이동
    public String signupForm(Model model) {
        model.addAttribute("user", new User()); // 폼에서 사용할 빈 User 객체 추가
        return "signupStep1"; // templates 폴더의 signup.html로 연결
    }

    @PostMapping("/signup/step1")
    public String signupStep1(@ModelAttribute("user") User user, Model model, HttpSession session) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "이미 존재하는 아이디 입니다.");
            return "signupStep1";
        }

        //세션에 username 저장
        session.setAttribute("user", user);
        return "redirect:/auth/signup/step2";

    }
    @GetMapping("/signup/step2")
    public String signupStep2Form(HttpSession session , Model model){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/auth/signup/step1";// 세션에 데이터가 없으면 첫 단계로 이동
        }
        model.addAttribute("user", user);
        return "signupStep2"; // 두번째 단계 폼
    }
    @PostMapping("/signup/step2")
    public String signupStep2(@ModelAttribute("user") User user, Model model , HttpSession session){
        if(!user.getPassword().equals(user.getConfirmPassword())){
            model.addAttribute("error", "비밀번호가 일치 하지 않습니다.");
            return "signupStep2";
        }
        //세션에 비밀번호 저장하는 부분
        User sessionUser = (User) session.getAttribute("user");
        sessionUser.setPassword(user.getPassword());
        sessionUser.setConfirmPassword(user.getConfirmPassword());
        session.setAttribute("user", sessionUser);
        return "redirect:/auth/signup/step3";
    }
    // step 3: 이메일 입력
    @GetMapping("/signup/step3")
    public String signupStep3Form(HttpSession session , Model model){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/auth/signup/step1";
        }
        model.addAttribute("user", user);
        return "signupStep3";
    }
    @PostMapping("/signup/step3")
    public String signupstep3(@ModelAttribute("user") User user, Model model, HttpSession session){
        //세션에 이메일 저장
        User sessionUser = (User) session.getAttribute("user");
        sessionUser.setEmail(user.getEmail());
        session.setAttribute("user", sessionUser);
        return "redirect:/auth/signup/step4";
    }
    // Step 4: 전화번호 입력
    @GetMapping("/signup/step4")
    public String signupStep4Form(HttpSession session , Model model){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/auth/signup/step1";
        }
        model.addAttribute("user", user);
        return "signupStep4";
    }
    @PostMapping("/signup/step4")
    public String signupStep4(@ModelAttribute("user") User user, Model model, HttpSession session){
        //세션에 전화번호 저장
        User sessionUser = (User) session.getAttribute("user");
        sessionUser.setPhone(user.getPhone());

        //모든 정보 저장 완료후 , MongoDB에 저장
        userService.save(sessionUser);
        session.removeAttribute("user");
        return "redirect:/auth/login";
    }
}


