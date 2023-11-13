package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.ItemSearchDto;
import com.mall.twins.twinsmall.dto.MainItemDto;
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

    @GetMapping("/index")
    public void list(){
        log.info("list....");

    }

    @GetMapping("/aboutus")
    public String aboutus(){
        return "aboutus";

    }//

    @GetMapping("/checkout")
    public String checkout(){
        return "checkout";

    }

    @GetMapping("/cart")
    public String cart(){
        return "cart";

    }

    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }





}