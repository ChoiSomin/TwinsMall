package com.mall.twins.twinsmall.service;


import com.mall.twins.twinsmall.constant.ItemSellStatus;
import com.mall.twins.twinsmall.dto.CartItemDto;
import com.mall.twins.twinsmall.entity.CartItem;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.CartItemRepository;
import com.mall.twins.twinsmall.repository.ItemRepository;
import com.mall.twins.twinsmall.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemRepository cartItemRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setPname("테스트상품");
        item.setPprice(100000);
        item.setPdesc("테스트상품입니다.");
        item.setPstatus(ItemSellStatus.SELL);
        item.setPstock(50);
        item.setPcate("냉장고");
        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setMid("member10");
        member.setMpw("1111");
        member.setMname("테스트");
        member.setMemail("email@aaa.bbb");
        member.setMphone("010-0000-0000");
        return memberRepository.save(member);
    }

    @Test
    public void addCart(){
        Item item = saveItem();
        Member member = saveMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        Long cartItemId = cartService.addCart(cartItemDto,member.getMid());

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(),cartItem.getItem().getId());
        assertEquals(cartItemDto.getCount(),cartItem.getCount());
    }
}
