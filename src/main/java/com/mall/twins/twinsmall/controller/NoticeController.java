package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping(value = "/list")
    public String notice(Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<NoticeFormDto> notices = noticeService.getNoticeList(pageable);

        model.addAttribute("notice", notices);
        model.addAttribute("maxPage", 5);

        return "notice/notice";
    }
    @GetMapping(value = "/register")
    public String NoticeForm(Model model){
        model.addAttribute("noticeFormDto", new NoticeFormDto());

        return "notice/noticeRegister";
    }

    @PostMapping(value = "/register")
    public String NoticeNew(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult,
                            Model model){

        if(bindingResult.hasErrors()){
            return "notice/noticeRegister";
        }

        try {
            noticeService.saveNotice(noticeFormDto);
        }catch (Exception e){
            model.addAttribute("errorMessage", "등록 중 오류가 발생하였습니다.");
            return "notice/noticeRegister";
        }

        return "redirect:/notice/list";
    }

    @GetMapping(value = "/{NoticeNid}")
    public String noticeDtl(@PathVariable("NoticeNid") Long NoticeNid, Model model){
        try{
            NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(NoticeNid); // 조회한 상품 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("noticeFormDto", noticeFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 상품입니다.");
            /*model.addAttribute("noticeFormDto", new NoticeFormDto());*/

            return "notice/noticeRegister";
        }
        return "notice/noticeRegister";
    }

    @PostMapping(value = "/{NoticeNid}")
    public String noticeUpdate(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "notice/noticeRegister";
        }

        try {
            noticeService.updateNotice(noticeFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "notice/noticeRegister";
        }

        return "redirect:/";
    }



}
