package com.mall.twins.twinsmall.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CartDetailDto {

    private Long cartItemId;    //장바구니 상품 아이디

    private String itemNm;  //상품명

    private int price;  //상품금액

    private int count; //수량

    private String imgUrl;  //상품이미지 경로

    private String orderTotalPrice;

    private List<Long> cartItemIds; //배열 추가


    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl){
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }

    public CartDetailDto() {
        // 기본 생성자 내용이 필요 없을 경우 비워둘 수도 있습니다.
    }

    private List<CartItemDto> cartDetailDto;

    public String getOrderTotalPrice() {

        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }




}