package com.example.redunm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final KakaoPayService kakaoPayService;

    @PostMapping("/pay/ready")
    public ResponseEntity<?> payReady(@RequestBody OrderCreateForm orderCreateForm) {
        try {
            String name = orderCreateForm.getName();
            int totalPrice = orderCreateForm.getTotalPrice();

            // 유효성 검사
            if (name == null || name.isEmpty() || totalPrice <= 0) {
                log.error("유효하지 않은 주문 정보");
                return ResponseEntity.badRequest().body("유효하지 않은 주문 정보입니다.");
            }

            log.info("주문 상품 이름: " + name);
            log.info("주문 금액: " + totalPrice);

            // 카카오 결제 준비하기
            ReadyResponse readyResponse = kakaoPayService.payReady(name, totalPrice);

            // 세션에 결제 고유번호(tid) 저장
            SessionUtils.addAttribute("tid", readyResponse.getTid());
            log.info("결제 고유번호: " + readyResponse.getTid());

            return ResponseEntity.ok(readyResponse);
        } catch (Exception e) {
            log.error("결제 준비 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("결제 준비 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/pay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken, Model model) {
        try {
            // pg_token이 없거나 비어있으면 리다이렉트 할 필요 없으므로 오류 처리
            if (pgToken == null || pgToken.isEmpty()) {
                log.error("pg_token이 존재하지 않거나 유효하지 않습니다.");
                return "error";  // 오류 페이지로 이동
            }

            String tid = SessionUtils.getStringAttributeValue("tid");

            if (tid == null) {
                log.error("세션에 결제 고유번호(tid)가 없습니다.");
                return "error";  // 오류 페이지로 이동
            }

            log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
            log.info("결제 고유번호: " + tid);

            // 카카오 결제 승인 요청
            ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);

            if (approveResponse != null && approveResponse.getAmount() != null) {
                log.info("결제 승인 완료: " + approveResponse);

                // 모델에 approveResponse 객체를 담아서 뷰로 전달
                model.addAttribute("approveResponse", approveResponse);

                return "completed";  // completed.html로 이동
            } else {
                log.error("결제 승인 실패");
                return "error";  // 오류 페이지로 이동
            }
        } catch (Exception e) {
            log.error("결제 승인 중 오류 발생: ", e);
            return "error";  // 오류 페이지로 이동
        }
    }
}
