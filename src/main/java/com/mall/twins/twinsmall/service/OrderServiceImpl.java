package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.OrderDto;
import com.mall.twins.twinsmall.dto.OrderHistDto;
import com.mall.twins.twinsmall.dto.OrderItemDto;
import com.mall.twins.twinsmall.entity.*;
import com.mall.twins.twinsmall.repository.ItemImgRepository;
import com.mall.twins.twinsmall.repository.ItemRepository;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

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

    private final ItemImgRepository itemImgRepository;

    @Override
    public Long order(OrderDto orderDto, String mid) {


        //주문할 상품을 조회
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        //현재 로그인한 회원의 아이드를 ㅣ용하여 회원정보 조회
        Member member = memberRepository.findByMid(mid);

        List<OrderItem> orderItemList = new ArrayList<>();
        //주문할 상품 엔티티와 주문수량을 이용하여 주문 상품 엔티티 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        //회원정보와 주문할 상품리스트 정보를 이용하여 주문 엔티티 생성
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);    //생성한 주문 엔티티를 저장
        return order.getOno();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String mid, Pageable pageable) {

        //유저의 아이디와 페이징조건을 이용하여 주문 목록을 조회합니다.
        List<Order> orders = orderRepository.findOrders(mid, pageable);
        //유저의 주문 총개수를 구합니다.
        Long totalCount = orderRepository.countOrder(mid);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        //주문 리스트를 순회하면서 구매이력페이지에 전달할 DTO를 생성합니다.
        for(Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems){
                //주문한 상품의 대표 이미지를 조회합니다.
                ItemImg itemImg = itemImgRepository.findByIdAndIimgrep(orderItem.getItem().getId(),"Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getIimgurl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        //페이지 구현 객체를 생성하여 반환합니다.
        return new PageImpl<OrderHistDto>(orderHistDtos,pageable,totalCount);
    }

    @Override
    public Boolean validateOrder(Long ono, String mid) {

        //현재 로그인한 사용자와 주문데이터를 생성한 사용자가 같은지 검사를 합니다. 같을때는 true를 반환하고 같지 않을 경우는 false를 반환
        Member curMember = memberRepository.findByMid(mid);
        Order order = orderRepository.findById(ono).orElseThrow(EntityNotFoundException :: new);

        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getMid(),savedMember.getMid())){
            return false;
        }
        return true;
    }

    @Override
    public void cancelOrder(Long ono) {
        Order order = orderRepository.findById(ono).orElseThrow(EntityNotFoundException :: new);
        //주문 취소 상태로 변경하여 변경감지 기능에 의해서 트랜잭션이 끝날때 update쿼리가 실행한다.
        order.cancelOrder();
    }

    @Override
    public Long orders(List<OrderDto> orderDtoList, String mid) {

        Member member = memberRepository.findByMid(mid);
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderDto orderDto : orderDtoList){
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item,orderDto.getCount());
            orderItemList.add(orderItem);
        }
        Order order = Order.createOrder(member,orderItemList);
        orderRepository.save(order);
        return order.getOno();
    }


}