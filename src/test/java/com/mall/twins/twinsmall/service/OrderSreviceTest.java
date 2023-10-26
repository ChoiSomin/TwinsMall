package com.mall.twins.twinsmall.service;



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
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import com.mall.twins.twinsmall.constant.ItemSellStatus;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
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
        item.setPname("테스트1");
        item.setPprice(10000);
        item.setPdesc("테스트상품임");
        item.setPcate("냉장고");
        item.setPstatus(ItemSellStatus.SELL);
        item.setPstock(100);

        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setMid("aaaa");
        member.setMpw("1111");
        member.setMemail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문테스트")
    public void order(){
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setOquantity(10);
        orderDto.setPname(item.getPname());

        Long ono = orderService.order(orderDto, member.getMid());

        Order order = orderRepository.findById(ono).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getOquantity()*item.getPprice();

    }
}
