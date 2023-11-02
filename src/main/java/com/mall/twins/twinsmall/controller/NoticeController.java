package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.ItemFormDto;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping(value = "/admin/notice/register")
    public String NoticeForm(Model model){
        model.addAttribute("noticeFromDto", new NoticeFormDto());
        return "notice/noticeForm";
    }

    @PostMapping(value = "/admin/notice/register")
    public String NoticeNew(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult,
                            Model model, @RequestParam("noticeImgFile") List<MultipartFile> noticeImgFileList){

        if(bindingResult.hasErrors()){
            return "notice/noticeForm";
        }

        if(noticeImgFileList.get(0).isEmpty() && noticeFormDto.getNno() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입니다");
            return "notice/noticeForm";
        }

        try {
            noticeService.saveNotice(noticeFormDto, noticeImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "등록중 오류가 발생하였습니다.");
            return "notice/noticeFrom";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/notice/{NoticeNid}")
    public String noticeDtl(@PathVariable("NoticeNid") Long NoticeNid, Model model){
        try{
            NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(NoticeNid); // 조회한 상품 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("noticeFormDto", noticeFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 상품입니다.");
            model.addAttribute("noticeFormDto", new NoticeFormDto());

            return "notice/noticeForm";
        }
        return "notice/noticeForm";
    }

    @PostMapping(value = "/admin/notice/{NoticeNid}")
    public String noticeUpdate(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult,
                             @RequestParam("noticeImgFile") List<MultipartFile> noticeImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "notice/noticeForm";
        }

        if(noticeImgFileList.get(0).isEmpty() && noticeFormDto.getNno() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
            return "notice/noticeForm";
        }

        try {
            noticeService.updateNotice(noticeFormDto, noticeImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "notice/noticeForm";
        }

        return "redirect:/";
    }



}
