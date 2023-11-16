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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "item/register")
    public String itemRegister(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/reigster";
    }

    @PostMapping(value = "item/itemRegister")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){
            return "item/reigster";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/reigster";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/reigster";
        }

        return "redirect:/index";
    }

    @GetMapping(value = "/admin/item/{ItemId}")
    public String itemDtl(@PathVariable("ItemId") Long ItemId, Model model){
        try{
            ItemFormDto itemFormDto = itemService.getItemDtl(ItemId); // 조회한 상품 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());

            return "item/reigster";
        }
        return "item/reigster";
    }

    @PostMapping(value = "/admin/item/{ItemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "item/reigster";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/reigster";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/reigster";
        }

        return "redirect:/index";
    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<Item> items =
                itemService.getAdminItemPage(itemSearchDto, pageable);
                model.addAttribute("items", items);
                model.addAttribute("itemSearchDto", itemSearchDto);
                model.addAttribute("maxPage", 5);

                return "item/itemMng";

    }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "item/itemDtl";
    }

    @GetMapping(value = "/item/list")
    public String product(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        log.info("ItemSearchDto: {}", itemSearchDto);

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 12);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);


        return "item/list";
    }

    @GetMapping(value = "/item/detail/{itemId}")
    public String productDetail(Model model, @PathVariable("itemId") Long itemId, Principal principal){

        if (principal != null){
            String username = principal.getName();

            model.addAttribute("username", username);
            return "item/detail";
        }
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);

        return "item/detail";
    }

}
