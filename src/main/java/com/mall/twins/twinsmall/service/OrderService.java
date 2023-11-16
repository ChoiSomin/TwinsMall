package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.OrderDto;
import com.mall.twins.twinsmall.dto.OrderHistDto;
import com.mall.twins.twinsmall.dto.OrderItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
//@RequiredArgsConstructor
public interface OrderService {

    Long order(OrderDto orderDto, String mid);

    Page<OrderHistDto> getOrderList(String mid, Pageable pageable);

    Boolean validateOrder(Long ono,String mid);

    void cancelOrder(Long ono);

    Long orders(List<OrderDto> orderDtoList, String mid);



}