package com.example.redunm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateForm {
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String name;

    @Min(value = 1, message = "총 가격은 0보다 큰 값이어야 합니다.")
    private int totalPrice;
}
