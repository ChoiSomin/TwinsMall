package com.mall.twins.twinsmall.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.twins.twinsmall.dto.OrderDto;
import com.mall.twins.twinsmall.dto.OrderHistDto;
import com.mall.twins.twinsmall.dto.ShippingDto;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    private final MemberRepository memberRepository;

    @PostMapping(value = "/order")
    //RequestBody: HTTP요청의 본문 body에 담긴 내용을 자바 객체로 전달
    //ResponseBody : 자바 객체를 HTTP요청의 body로 전달
    public @ResponseBody ResponseEntity order (@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){

        log.info("orderDto : " + orderDto);

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

    @GetMapping(value = "/detail/checkout")
    public String orderDetailItem(@RequestParam(name = "orderData") String orderData, Model model, Principal principal) {
        log.info("유저 아이디: " + principal.getName());

        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 데이터에서 바로 OrderDTO를 읽어옴
            OrderDto orderDTO = objectMapper.readValue(orderData, OrderDto.class);

            log.info("OrderDto : " + orderDTO);

            int orderTotalPrice = orderDTO.getCount() * orderDTO.getPrice();

            log.info("orderTotalPrice : " + orderTotalPrice);

            // 여기서 주문 처리 페이지에 필요한 데이터를 모델에 추가
            model.addAttribute("member", member);
            model.addAttribute("shippingDto", new ShippingDto());
            model.addAttribute("cartItems", orderDTO);
            model.addAttribute("totalOrderPrice", orderTotalPrice);

            // checkout 페이지로 이동
            return "checkout";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = {"/orders","/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
        //한번에 가지고 올 주문의 개수는 4개로 설정
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);

        //현재로그인한 회원은 이메일과 페이징 객체를 파라미터로 전달하여 화면에 전달한 주문 목록 데이터를 리터값으로 받습니다.
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        log.info("dto = " + orderHistDtoList);

        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage",5);

        return "order/orderList";
    }

    @PostMapping("/order/{ono}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("ono") Long ono, Principal principal){
        //자바스크립트에서 취소할 주문 번호는 조작이 가능하므로 다른 사람의 주문을 취소하지 못하도록 주문 취소 권한을 검사한다.
        if(!orderService.validateOrder(ono,principal.getName())){
            return new ResponseEntity<String>("주문취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        //주문 취소 로직을 호출한다.
        orderService.cancelOrder(ono);
        return new ResponseEntity<Long>(ono,HttpStatus.OK);
    }

    @GetMapping(value = {"order/orderDetail/{ono}"})
    public String orderDetail(@PathVariable Long ono, @RequestParam(name = "page", defaultValue = "0") int page,
                              Principal principal, Model model, Pageable pageable) {

        String mid = principal.getName();

        Pageable pageRequest = PageRequest.of(page, 10); // 적절한 페이지 정보 설정
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderListByOrderId(mid, ono, pageRequest);
        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("order", ono);
        model.addAttribute("currentPage", page);
        model.addAttribute("maxPage", 5);
        return "order/orderDetail";
    }
}