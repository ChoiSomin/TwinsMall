package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.ItemFormDto;
import com.mall.twins.twinsmall.dto.ItemSearchDto;
import com.mall.twins.twinsmall.dto.MainItemDto;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.service.ItemService;
import com.mall.twins.twinsmall.service.MemberService;
import com.mall.twins.twinsmall.service.MemberServiceImpl;
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
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/item/register")
    public String itemRegister(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/register";
    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String adminMember(ItemSearchDto itemSearchDto, @PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<Item> items =
                itemService.getAdminItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "admin/itemMng";
    }

    @GetMapping(value = "/admin/memberList")
    public String adminPage(PageRequestDTO pageRequestDTO, @RequestParam(value="page", defaultValue="0") int page, Model model){

        log.info("Member list...." + pageRequestDTO);

        Page<Member> paging = this.memberService.getList(page);
        List<Member> memberList = this.memberRepository.findAll();

        model.addAttribute("member", memberService.getMemberList(pageRequestDTO));
        model.addAttribute("paging", paging);
        model.addAttribute("memberList", memberList);

        log.info(memberService.getMemberList(pageRequestDTO));

        return "admin/memberList";
    }

    @PostMapping(value = "/item/register")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){
            return "item/register";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/register";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/register";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/item/{ItemId}")
    public String itemDtl(@PathVariable("ItemId") Long ItemId, Model model){
        try{
            ItemFormDto itemFormDto = itemService.getItemDtl(ItemId); // 조회한 상품 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());

            return "item/register";
        }
        return "item/register";
    }

    @PostMapping(value = "/admin/item/{ItemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "item/register";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/register";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/register";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/item/list")
    public String product(@RequestParam(name = "pname", required = false) String pname,
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

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 12);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/list";
    }

    @GetMapping(value = "/item/detail/{itemId}")
    public String productDetail(Model model, @PathVariable("itemId") Long itemId, Principal principal){

        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        if (itemFormDto == null) {
            // Handle the case where item is not found (null)
            // For example, you can redirect to an error page or homepage
            return "redirect:/"; // Redirect to homepage
        }

        model.addAttribute("item", itemFormDto);

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
        }

        return "item/detail";

    }

}
