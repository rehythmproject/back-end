package com.example.redunm.controller;

import com.example.redunm.dto.ApproveResponse;
import com.example.redunm.dto.OrderCreateForm;
import com.example.redunm.dto.ReadyResponse;
import com.example.redunm.service.KakaoPayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final KakaoPayService kakaoPayService;


    @PostMapping("/pay/ready")
    public ResponseEntity<?> payReady(@Valid @RequestBody OrderCreateForm orderCreateForm) {
        String name = orderCreateForm.getName();
        BigDecimal totalPrice = orderCreateForm.getTotalPrice();

        if (name == null || name.isEmpty() || totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("유효하지 않은 주문 정보입니다."));
        }

        try {
            ReadyResponse readyResponse = kakaoPayService.payReady(name, totalPrice);
            return ResponseEntity.ok(readyResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("결제 준비 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @GetMapping("/pay/completed")
    public ResponseEntity<?> payCompleted(@RequestParam("pg_token") String pgToken,
                                          @RequestParam("tid") String tid) {
        if (pgToken == null || pgToken.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("pg_token이 존재하지 않거나 유효하지 않습니다."));
        }

        if (tid == null || tid.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("tid가 존재하지 않거나 유효하지 않습니다."));
        }

        try {
            ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);

            if (approveResponse != null && approveResponse.getAmount() != null) {
                return ResponseEntity.ok(approveResponse);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("결제 승인 실패"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("결제 승인 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse() {}

        public ErrorResponse(String message) {
            this.message = message;
        }

        // Getter and Setter
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
