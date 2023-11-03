package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.ReviewFormDto;
import com.mall.twins.twinsmall.service.NoticeService;
import com.mall.twins.twinsmall.service.ReviewService;
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
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(value = "/mypage/review/register")
    public String ReivewForm(Model model){
        model.addAttribute("reviewFromDto", new ReviewFormDto());
        return "review/reviewForm";
    }

    @PostMapping(value = "/mypage/review/register")
    public String ReviewNew(@Valid ReviewFormDto reviewFormDto, BindingResult bindingResult,
                            Model model, @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList){

        if(bindingResult.hasErrors()){
            return "review/reviewForm";
        }

        if(reviewImgFileList.get(0).isEmpty() && reviewFormDto.getRno() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입니다");
            return "review/reviewForm";
        }

        try {
            reviewService.saveReview(reviewFormDto, reviewImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "등록중 오류가 발생하였습니다.");
            return "review/reviewForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/mypage/reivew/{ReviewRid}")
    public String noticeDtl(@PathVariable("ReviewRid") Long ReviewRid, Model model){
        try{
            ReviewFormDto reviewFormDto = reviewService.getReviewDtl(ReviewRid); // 조회한 상품 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("reviewFormDto", reviewFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 상품입니다.");
            model.addAttribute("reivewFormDto", new ReviewFormDto());

            return "review/reviewForm";
        }
        return "review/reviewForm";
    }

    @PostMapping(value = "/mypage/review/{ReviewNid}")
    public String reivewUpdate(@Valid ReviewFormDto reviewFormDto, BindingResult bindingResult,
                               @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "review/reviewForm";
        }

        if(reviewImgFileList.get(0).isEmpty() && reviewFormDto.getRno() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
            return "review/reviewForm";
        }

        try {
            /*reviewService.updateReview(reviewFormDto, reviewImgFileList);*/
        } catch (Exception e){
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "review/reviewForm";
        }

        return "redirect:/";
    }



}
