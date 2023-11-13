package com.mall.twins.twinsmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Log4j2
public class MypageController {

    @GetMapping("/order")
    public void list(){
        log.info("list....");

    }
}
