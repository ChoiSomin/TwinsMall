package com.mall.twins.twinsmall.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/member")
@Log4j2
public class TwinsMallController {

    @GetMapping("/")
    public void list(){
        log.info("===========test===========");

    }
}
