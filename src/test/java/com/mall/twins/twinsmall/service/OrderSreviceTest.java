package com.mall.twins.twinsmall.service;



import com.mall.twins.twinsmall.constant.Role;
import com.mall.twins.twinsmall.dto.OrderDto;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Order;
import com.mall.twins.twinsmall.entity.OrderItem;
import com.mall.twins.twinsmall.repository.ItemRepository;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import com.mall.twins.twinsmall.constant.ItemSellStatus;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class OrderSreviceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setPname("테스트상품");
        item.setPprice(10000);
        item.setPdesc("테스트 상품 상세 설명");
        item.setPstatus(ItemSellStatus.SELL);
        item.setPcate("TV");
        item.setPstock(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setMid("aaaa");
        member.setMpw("1111");
        member.setMname("최소민");
        member.setMbirth("19930518");
        member.setMemail("test@test.com");
        member.setMphone("0100000000");
        member.setRole(Role.USER);

        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문테스트")
    public void order(){
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setOquantity(10);
        orderDto.setItemId(item.getId());

        Long ono = orderService.order(orderDto, member.getMid());

        Order order = orderRepository.findById(ono).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getOquantity()*item.getPprice();

        assertEquals(totalPrice, order.getTotalPrice());

    }
}
