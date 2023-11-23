package com.mall.twins.twinsmall.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {

    @NotNull(message = "상품아이디는 필수 입력값입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private int count;

    private Long cartItemId;

    private String itemNm;

    private String price;

    private String imgUrl;
}
