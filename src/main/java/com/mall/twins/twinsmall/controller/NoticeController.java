package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.service.NoticeService;
import com.mall.twins.twinsmall.service.NoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
@Log4j2
public class NoticeController{

    private final NoticeService noticeService;

    private final NoticeServiceImpl noticeServiceImpl;

    @GetMapping(value = "/list")
    public String notice(PageRequestDTO pageRequestDTO, Model model){

        log.info("Notice list...." + pageRequestDTO);

        model.addAttribute("notice", noticeService.getNoticeList(pageRequestDTO));
        return "notice/notice";
    }
    @GetMapping(value = "/register")
    public void NoticeForm(){
        /*model.addAttribute("noticeFormDto", new NoticeFormDto());*/
    }

    @PostMapping(value = "/register")
    public String NoticeNew(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult,
                            Model model){

        if(bindingResult.hasErrors()){
            return "notice/register";
        }

        try {
            Long nno = noticeService.register(noticeFormDto);
            model.addAttribute("msg", nno);
        }catch (Exception e){
            model.addAttribute("errorMessage", "등록 중 오류가 발생하였습니다.");
            return "notice/register";
        }

        return "redirect:/notice/list";
    }

    /*@GetMapping(value = "/{nno}")
    public String noticeDtl(@PathVariable("nno") Long nno, Model model){
        try{
            NoticeFormDto noticeFormDto = noticeServiceImpl.getNoticeDtl(nno); // 조회한 공지사항 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("noticeFormDto", noticeFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 게시글입니다.");
            *//*model.addAttribute("noticeFormDto", new NoticeFormDto());*//*

            return "notice/notice";
        }
        return "notice/noticeDetail";
    }*/

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long nno, Model model){
        log.info("notice id : " + nno);

        NoticeFormDto noticeFormDto = noticeService.read(nno);

        log.info(noticeFormDto);

        model.addAttribute("noticeFormDto", noticeFormDto);
    }

   /* @PostMapping(value = "/{nid}")
    public String noticeUpdate(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "notice/noticeRegister";
        }

        try {
            noticeServiceImpl.updateNotice(noticeFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "notice/noticeRegister";
        }

        return "redirect:/";
    }*/

    @PostMapping ("/remove")
    public String remove(Long nno, RedirectAttributes redirectAttributes){

        log.info(nno);

        noticeService.remove(nno);

        redirectAttributes.addFlashAttribute("msg", nno);

        //게시물삭제하고 게시물리스트로 넘어가야하므로
        return "redirect:/notice/list";
    }

    @PostMapping("/modify")
    public String modify(NoticeFormDto noticeFormDto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){


        log.info("post modify.........................................");
        log.info("noticeFormDto: " + noticeFormDto);

        noticeService.modify(noticeFormDto);

        model.addAttribute("page", requestDTO.getPage());
        model.addAttribute("type", requestDTO.getType());
        model.addAttribute("keyword", requestDTO.getKeyword());

        model.addAttribute("nno",noticeFormDto.getNno());

        log.debug("modify method called!"); // 추가

        return "notice/read";

    }

}
