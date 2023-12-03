package com.mall.twins.twinsmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class CartOrderDto {

    private Long cartItemId;

    private String address;

    private List<CartOrderDto> cartOrderDtoList;
}
