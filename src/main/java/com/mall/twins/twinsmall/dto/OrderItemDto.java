package com.mall.twins.twinsmall.dto;


import com.mall.twins.twinsmall.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private String itemNm; //상품명
    private int count;  //주문 수량
    private int orderPrice; //주문 금액
    private String imgUrl;  //상품이미지 경로

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemNm = orderItem.getItem().getPname();
        this.count = orderItem.getOiquantity();
        this.orderPrice = orderItem.getOiprice();
        this.imgUrl = imgUrl;
    }
}
