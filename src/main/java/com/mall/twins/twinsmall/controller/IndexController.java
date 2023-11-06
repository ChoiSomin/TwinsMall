package com.mall.twins.twinsmall.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class IndexController {

    @GetMapping({"/index", "/aboutAs", "/join", "login", "product", "productDetail"})
    public void list(){
        log.info("IndexController.list() 인덱스 페이지 호출");

    }


}
