package com.mall.twins.twinsmall.controller;


import com.mall.twins.twinsmall.dto.OrderDto;
import com.mall.twins.twinsmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    //RequestBody: HTTP요청의 본문 body에 담긴 내용을 자바 객체로 전달
    //ResponseBody : 자바 객체를 HTTP요청의 body로 전달
    public @ResponseBody ResponseEntity order (@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){//주문 정보를 받는 orderDTO 객체에 데이터 바인딩시 에러가 있는지 검사
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(),HttpStatus.BAD_REQUEST);    // 에러장보를 담아서 반환
        }
        String mid = principal.getName(); // principal객체에서 현재 로그인한 회원의 아이디 정보를 조회한다.
        Long ono;
        try{
            ono = orderService.order(orderDto, mid);    //화면으로 부터 넘어오는 주문정보와 회원의 아이디 정보를 이용하여 주문 로직 호출
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(ono, HttpStatus.OK);    //결과값으로 생성된 주문번호와 요청이 성공했다는 HTTP응답 상ㅌ태코드 반환
    }
}
