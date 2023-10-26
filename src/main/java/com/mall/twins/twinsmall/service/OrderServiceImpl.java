package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.OrderDto;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Order;
import com.mall.twins.twinsmall.entity.OrderItem;
import com.mall.twins.twinsmall.repository.ItemRepository;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Override
    public Long order(OrderDto orderDto, String mid) {
        //주문할 상품을 조회
        Item item = itemRepository.findById(orderDto.getPname());
        //현재 로그인한 회원의 아이드를 ㅣ용하여 회원정보 조회
        Member member = memberRepository.findByMid(mid);

        List<OrderItem> orderItemList = new ArrayList<>();
        //주문할 상품 엔티티와 주문수량을 이용하여 주문 상품 엔티티 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getOquantity());
        orderItemList.add(orderItem);

        //회원정보와 주문할 상품리스트 정보를 이용하여 주문 엔티티 생성
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);    //생성한 주문 엔티티를 저장
        return order.getOno();
    }
}
