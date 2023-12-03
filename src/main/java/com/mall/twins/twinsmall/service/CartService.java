package com.mall.twins.twinsmall.service;


import com.mall.twins.twinsmall.dto.CartDetailDto;
import com.mall.twins.twinsmall.dto.CartItemDto;
import com.mall.twins.twinsmall.dto.CartOrderDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CartService {

    Long addCart(CartItemDto cartItemDto, String mid);

    List<CartDetailDto> getCartList(String mid);

    boolean validateCartItem(Long cartItemId, String mid);

    void updateCartItemCount(Long cartItemId, int count);

    void deleteCartItem(Long cartItemId);

    Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String mid);

    Long orderCheckoutItem(List<CartOrderDto> cartOrderDtoList, String mid, String address);
}