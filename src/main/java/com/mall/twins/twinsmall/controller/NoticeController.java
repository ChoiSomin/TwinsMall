package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.ItemSearchDto;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.NoticeSearchDto;
import com.mall.twins.twinsmall.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping(value = "/list")
    public String notice(NoticeSearchDto noticeSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<NoticeFormDto> notices = noticeService.getNoticeList(noticeSearchDto, pageable);

        model.addAttribute("notice", notices);
        model.addAttribute("noticeSearchDto", noticeSearchDto);
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

    @GetMapping(value = "/{nid}")
    public String noticeDtl(@PathVariable("nid") Long nid, Model model){
        try{
            NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(nid); // 조회한 공지사항 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("noticeFormDto", noticeFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 게시글입니다.");
            /*model.addAttribute("noticeFormDto", new NoticeFormDto());*/

            return "notice/notice";
        }
        return "notice/noticeDetail";
    }

   /* @PostMapping(value = "/{nid}")
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
    }*/

    @PostMapping ("/remove")
    public String remove(NoticeSearchDto noticeSearchDto, Optional<Integer> page, @RequestParam("nid") Long nid, Model model){

        log.info(nid);

        noticeService.remove(nid);

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<NoticeFormDto> notices = noticeService.getNoticeList(noticeSearchDto, pageable);

        model.addAttribute("notice", notices);
        model.addAttribute("noticeSearchDto", noticeSearchDto);
        model.addAttribute("maxPage", 5);

        //게시물삭제하고 게시물리스트로 넘어가야하므로
        return "notice/notice";
    }

    @PostMapping("/modify")
    public String modify(NoticeFormDto dto, Model model){


        log.info("post modify.........................................");
        log.info("dto: " + dto);

        noticeService.modify(dto);

        model.addAttribute("id",dto.getNno());

        return "redirect:/review/read";

    }

}
