package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.CartDetailDto;
import com.mall.twins.twinsmall.dto.CartItemDto;
import com.mall.twins.twinsmall.dto.CartOrderDto;
import com.mall.twins.twinsmall.dto.OrderDto;
import com.mall.twins.twinsmall.entity.Cart;
import com.mall.twins.twinsmall.entity.CartItem;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.CartItemRepository;
import com.mall.twins.twinsmall.repository.CartRepository;
import com.mall.twins.twinsmall.repository.ItemRepository;
import com.mall.twins.twinsmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CartServiceImpl implements CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final OrderService orderService;

    @Override
    public Long addCart(CartItemDto cartItemDto, String mid) {
        
        //장바구니에 담을 ㅅ강품 엔티티를 조회합니다.
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException :: new);
        //현재 로그인한 회원 엔티티를 조회합니다.
        Member member = memberRepository.findByMid(mid);
        
        //현재 로그인한 회원의 장바구니 엔티티 조회
        Cart cart = cartRepository.findByMemberMno(member.getMno());
        //상품을 처음으로 장바구니에 담을 경우 해당 회원의 장바구니 엔티티 생성
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        
        //현재 상품이 장바구니에 이미 들어가 있는지 조사
        CartItem savedCartItem = cartItemRepository.findByCartCidAndItemId(cart.getCid(),item.getId());

        if(savedCartItem != null){
            //장바구니에 이미 있던 상품일 경우 기존수량에 현재 장바구니에 담을 수량 만큼 더해준다.
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getCino();
        }else{
            //장바구니 엔티티, 상품엔티티, 장바구니에 담을 수량을 이용하여 CartItem엔티티 생성
            CartItem cartItem = CartItem.createCartItem(cart,item,cartItemDto.getCount());
            //장바구니에 들어갈 상품을 저장
            cartItemRepository.save(cartItem);
            return cartItem.getCino();
        }
    }

    @Override
    public List<CartDetailDto> getCartList(String mid) {

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByMid(mid);
        Cart cart = cartRepository.findByMemberMno(member.getMno());
        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getCid());
        return cartDetailDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String mid) {
        Member curMember = memberRepository.findByMid(mid);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException :: new);
        Member saveMeber = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getMid(),saveMeber.getMid())){
            return  false;
        }
        return true;
    }

    @Override
    public void updateCartItemCount(Long cartItemId, int count) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException :: new);

        cartItem.updateCount(count);

    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String mid) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setOquantity(cartItem.getCount());
            orderDtoList.add(orderDto);
        }
        Long orderId = orderService.orders(orderDtoList,mid);

        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }
        return orderId;
    }
}
