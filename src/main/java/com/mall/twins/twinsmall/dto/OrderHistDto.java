package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.OrderStatus;
import com.mall.twins.twinsmall.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    private Long orderId;       //주문아이디
    private String orderDate;   //주문날짜
    private OrderStatus orderStatus; //주문상태

    private MemberJoinDTO memberJoinDTO;

    private int ordertotalPrice;
    //주문 상품 리스트
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public OrderHistDto(Order order){
        this.orderId = order.getOno();
        this.orderDate = order.getOdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOstatus();
        this.ordertotalPrice = order.getTotalPrice();
        this.memberJoinDTO = new MemberJoinDTO();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }




}