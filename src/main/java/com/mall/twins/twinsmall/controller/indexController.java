package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.ItemFormDto;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class indexController {

    private final ItemService itemService;

    @GetMapping("/")
    public String list(@RequestParam(name = "pname", required = false) String pname,
                       @RequestParam(name = "pcate", required = false) String pcate,
                       Optional<Integer> page, Model model) {

        ItemSearchDto itemSearchDto = new ItemSearchDto();

        if (pcate != null && !pcate.isEmpty()) {
            itemSearchDto.setPcate(pcate);
        }

        if (pname != null && !pname.isEmpty()) {
            itemSearchDto.setPname(pname);
        }

        log.info("ItemSearchDto: {}", itemSearchDto);

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 8);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "index2";
    }

    /*@GetMapping("/checkout")
    public void checkout() {
        log.info("checkout....");

    }*/

}