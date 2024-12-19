package com.example.redunm.service;

import com.example.redunm.dto.ApproveResponse;
import com.example.redunm.dto.ReadyResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoPayService {
    @Value("$kakao_api_key")
    private String apiKey;

    public ReadyResponse payReady(String name, @Min(value = 1, message = "총 가격은 0보다 큰 값이어야 합니다.") @Max(value = 10000000, message = "총 가격이 허용된 범위를 초과함") BigDecimal totalPrice) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");                                    // 가맹점 코드(테스트용)
        parameters.put("partner_order_id", "1234567890");                       // 주문번호
        parameters.put("partner_user_id", "roommake");                          // 회원 아이디
        parameters.put("item_name", name);                                      // 상품명
        parameters.put("quantity", "1");                                        // 상품 수량
        parameters.put("total_amount", String.valueOf(totalPrice));             // 상품 총액
        parameters.put("tax_free_amount", "0");                                 // 상품 비과세 금액
        parameters.put("approval_url", "http://localhost:8080/order/pay/completed"); // 결제 성공 시 URL
        parameters.put("cancel_url", "http://localhost:8080/order/pay/cancel");      // 결제 취소 시 URL
        parameters.put("fail_url", "http://localhost:8080/order/pay/fail");          // 결제 실패 시 URL

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        ResponseEntity<ReadyResponse> responseEntity = template.postForEntity(url, requestEntity, ReadyResponse.class);

        System.out.println("결제준비 응답객체: " + responseEntity.getBody());

        return responseEntity.getBody();
    }

    public ApproveResponse payApprove(String tid, String pgToken) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");              // 가맹점 코드(테스트용)
        parameters.put("tid", tid);                       // 결제 고유번호
        parameters.put("partner_order_id", "1234567890"); // 주문번호
        parameters.put("partner_user_id", "roommake");    // 회원 아이디
        parameters.put("pg_token", pgToken);              // 결제승인 요청을 인증하는 토큰

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
        ApproveResponse approveResponse = template.postForObject(url, requestEntity, ApproveResponse.class);

        System.out.println("결제승인 응답객체: " + approveResponse);

        return approveResponse;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + apiKey);
        headers.set("Content-type", "application/json");

        return headers;
    }
}
