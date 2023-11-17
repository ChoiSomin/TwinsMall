package com.mall.twins.twinsmall.controller;


import com.mall.twins.twinsmall.dto.CartDetailDto;
import com.mall.twins.twinsmall.dto.CartItemDto;
import com.mall.twins.twinsmall.dto.CartOrderDto;
import com.mall.twins.twinsmall.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto,
                                              BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){  //장바구니에 담을 상품정보를 받는 cartitemdto 객체에 데이터 바인딩시 에러가 있는지 검사
            StringBuilder sb = new StringBuilder();
           /*   List<FieldError> fieldErrors = bindingResult.getFieldErrors();
          for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }*/
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String mid = principal.getName(); //현재 로그인한 회원의 아이디에 정보를 변수에 저장
        Long cartItemId;
        try{
            //화면으로부터 넘어온 장바구니에 담을 상품 정보와 현재 로그인한 회원의 아이디 정보를 이용하여 장바구니에 상품을 담는 로직 호출
            cartItemId = cartService.addCart(cartItemDto, mid);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        //결과값으로 생성된 장바구닝 상품아이디와 요청이 성공했다는 응답 코드 반환
        return new ResponseEntity<Long>(cartItemId,HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model){
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems",cartDetailList);
        return "cart/cartList";
    }

    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId")
                                                       Long cartItemId, int count, Principal principal){

        if(count <= 0){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        }else if(!cartService.validateCartItem(cartItemId,principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.",HttpStatus.OK);
        }
        cartService.updateCartItemCount(cartItemId,count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId")Long cartItemId,Principal principal){
        if(!cartService.validateCartItem(cartItemId,principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.",HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId,HttpStatus.OK);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal){
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요.",HttpStatus.FORBIDDEN);
        }
        for(CartOrderDto cartOrder : cartOrderDtoList){
            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())){
                return new ResponseEntity<String>("주문 권한이 없습니다.",HttpStatus.FORBIDDEN);
            }
        }
        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId,HttpStatus.OK);
    }
}