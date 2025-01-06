package com.example.redunm.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateForm {
    @NotNull(message = "상품명은 필수 입력 값입니다.")
    private String name;

    @Min(value = 1, message = "총 가격은 0보다 큰 값이어야 합니다.")
    @Max(value = 10000000, message = "총 가격이 허용된 범위를 초과함")
    private BigDecimal totalPrice;
}
