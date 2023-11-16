package com.mall.twins.twinsmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class OrderDto {

    //상품 페이지에서 주문할 상품의 아이디와 주문 수량을 전달받을 DTO

    @NotNull(message = "상품 이름은 필수 입력값입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    @Max(value = 5, message = "최대 주문 수량은 5개 입니다.")
    private int count;


    private LocalDateTime odate; //주문일


}