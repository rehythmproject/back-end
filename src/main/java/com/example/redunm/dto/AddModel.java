package com.example.redunm.dto;

import com.example.redunm.constant.ModelSellStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddModel {
    private int id;

    @NotBlank(message = "모델 이름 입력은 필수 값 입니다.")
    private String ModelName;

    @NotBlank(message = "모델 설명은 필수 입력 값 입니다.")
    private String ModelDetail;

    @NotBlank(message = "모델 가격은 필수 입력 값 입니다.")
    private Integer ModelPrice;

    private ModelSellStatus modelSellStatus;

    private List<ModelImgDto> ModelImgDtoList = new ArrayList<>();

}
