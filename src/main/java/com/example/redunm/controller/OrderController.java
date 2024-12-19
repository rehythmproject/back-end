package com.example.redunm.controller;

import com.example.redunm.SessionUtils;
import com.example.redunm.dto.ApproveResponse;
import com.example.redunm.dto.OrderCreateForm;
import com.example.redunm.dto.ReadyResponse;
import com.example.redunm.service.KakaoPayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/pay/ready")
    public ResponseEntity<?> payReady(@Valid @RequestBody OrderCreateForm orderCreateForm) {
        try {
            String name = orderCreateForm.getName();
            BigDecimal totalPrice = orderCreateForm.getTotalPrice();

            if (name == null || name.isEmpty() || totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body("유효하지 않은 주문 정보입니다.");
            }

            ReadyResponse readyResponse = kakaoPayService.payReady(name, totalPrice);

            SessionUtils.addAttribute("tid", readyResponse.getTid());

            return ResponseEntity.ok(readyResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("결제 준비 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/pay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken, Model model) {
        try {
            if (pgToken == null || pgToken.isEmpty()) {
                model.addAttribute("error", "pg_token이 존재하지 않거나 유효하지 않습니다.");
                return "error"; // 오류 페이지로 이동
            }

            String tid = SessionUtils.getStringAttributeValue("tid");
            if (tid == null) {
                model.addAttribute("error", "세션에 결제 고유번호(tid)가 없습니다.");
                return "error"; // 오류 페이지로 이동
            }

            ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);

            if (approveResponse != null && approveResponse.getAmount() != null) {
                model.addAttribute("approveResponse", approveResponse);
                return "completed"; // completed.html로 이동
            } else {
                model.addAttribute("error", "결제 승인 실패");
                return "error"; // 오류 페이지로 이동
            }
        } catch (Exception e) {
            model.addAttribute("error", "결제 승인 중 오류가 발생했습니다: " + e.getMessage());
            return "error"; // 오류 페이지로 이동
        }
    }
}
