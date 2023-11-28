package com.mall.twins.twinsmall.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.twins.twinsmall.dto.CartDetailDto;
import com.mall.twins.twinsmall.dto.CartItemDto;
import com.mall.twins.twinsmall.dto.CartOrderDto;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.service.CartService;
import com.mall.twins.twinsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartService;

    private final MemberService memberService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto,
                                              BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {  //장바구니에 담을 상품정보를 받는 cartitemdto 객체에 데이터 바인딩시 에러가 있는지 검사
            StringBuilder sb = new StringBuilder();
           /*   List<FieldError> fieldErrors = bindingResult.getFieldErrors();
          for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }*/
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String mid = principal.getName(); //현재 로그인한 회원의 아이디에 정보를 변수에 저장
        Long cartItemId;
        try {
            //화면으로부터 넘어온 장바구니에 담을 상품 정보와 현재 로그인한 회원의 아이디 정보를 이용하여 장바구니에 상품을 담는 로직 호출
            cartItemId = cartService.addCart(cartItemDto, mid);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        //결과값으로 생성된 장바구닝 상품아이디와 요청이 성공했다는 응답 코드 반환
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model) {
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cart";
    }

    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId")
                                                       Long cartItemId, int count, Principal principal) {

        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.OK);
        }
        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {
        if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요.", HttpStatus.FORBIDDEN);
        }
        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if (!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }
        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    /*@PostMapping("/checkout")
    public @ResponseBody ResponseEntity checkout(@RequestBody CartDetailDto cartDetailDto, Principal principal , Model model) {

        log.info("cartDetailList : " + cartDetailDto);

        model.addAttribute("cartItems",cartDetailDto);

        return new ResponseEntity<>(cartDetailDto, HttpStatus.OK);
    }*/

    /*@GetMapping("/checkout")
    public ResponseEntity<CartOrderDto> checkoutget(@RequestBody CartOrderDto cartOrderDtoList, Principal principal , Model model) {

        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());

        log.info("CartOrderDto : " + cartOrderDtoList);

        model.addAttribute("cartItems",cartDetailList);

        return new ResponseEntity<>(cartOrderDtoList, HttpStatus.OK);
    }*/

    /*@GetMapping(value = "/checkout")
    public String checkout(Principal principal, Model model){
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());

        log.info("cartDetailList : " + cartDetailList);

        model.addAttribute("cartItems",cartDetailList);

        return "checkout";
    }*/

    @GetMapping("/checkout")
    public String checkout(@RequestParam(name = "orderData") String orderData, Model model) {
        log.info("orderData: " + orderData);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 데이터에서 바로 cartDetailDtoList를 읽어옴
            CartDetailDto cartDetailDto = objectMapper.readValue(orderData, CartDetailDto.class);

            // 숫자로 변환
            List<CartItemDto> cartItems = cartDetailDto.getCartDetailDto();
            cartItems.forEach(item -> item.setPrice(String.valueOf(Integer.parseInt(item.getPrice().replaceAll("[^0-9]", "")))));

            // 여기서 주문 처리 페이지에 필요한 데이터를 모델에 추가
            model.addAttribute("cartItems", cartItems);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // checkout.html로 이동
        return "checkout";
    }
}