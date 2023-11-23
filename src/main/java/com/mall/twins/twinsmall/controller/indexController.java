package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.ItemSearchDto;
import com.mall.twins.twinsmall.dto.MainItemDto;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class indexController {

    private final ItemService itemService;

    @GetMapping("/")
    public String list() {
        log.info("list....");
        return "index";
    }

    /*@GetMapping("/checkout")
    public void checkout() {
        log.info("checkout....");

    }*/

}