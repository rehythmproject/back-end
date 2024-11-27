package com.example.redunm.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// Response

@Getter // 같은 필드(변수 이름)의 값을 반환
@Setter // 매개변수로 전달된 값을 해당 필드에 저장.
@Data
public class ReadyResponse {
    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // 결제 요청 메시지 보내기 위한 사용자 정보 입력화면 Redirect_url
    private String created_at; // 결제 준비 요청 시간
}

