package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
//@RequiredArgsConstructor
public interface OrderService {

    Long order(OrderDto orderDto, String mid);


}
