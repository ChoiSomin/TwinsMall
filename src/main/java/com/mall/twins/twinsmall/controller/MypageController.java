package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.ShippingDTO;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import com.mall.twins.twinsmall.service.MemberService;
import com.mall.twins.twinsmall.service.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/mypage")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 조회 가능 -> 비로그인 상태일 경우 로그인 화면으로 이동
public class MypageController {

    private final MemberService memberService;

    private final ShippingService shippingService;

    // 회원정보 조회
    @GetMapping({"/index", "/modify"})
    public void read(Principal principal, Model model) {

        log.info("MemberController.read() 회원정보 읽기 페이지");
        log.info("유저 아이디 : " + principal.getName());

        String mid = principal.getName();
        MemberSecurityDTO memberSecurityDTO = memberService.readOne(mid);

        model.addAttribute("dto", memberSecurityDTO);
    }

    // 회원정보 수정
    @PostMapping("/modify")
    public String modify(@Valid MemberSecurityDTO memberSecurityDTO, Model model, RedirectAttributes redirectAttributes) {

        model.addAttribute("dto", memberSecurityDTO);

        log.info("MemberController.modify() 회원정보 수정 페이지");
        log.info("memberSecurityDTO 아이디, 비밀번호, 이름");
        log.info(memberSecurityDTO.getMid(), memberSecurityDTO.getMpassword(), memberSecurityDTO.getMname());

        redirectAttributes.addFlashAttribute("result", "modified");
        memberService.modify(memberSecurityDTO);

        return "redirect:/mypage/index";
    }

    // 배송지 관리 -> 모달창 폼을 띄워서 배송지 등록
    @GetMapping("/shipping")
    public void shippingGet(Model model) {

        log.info("MypageController.shippingGet() 배송지 관리 페이지");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String mid = userDetails.getUsername();

        // 배송 주소 전체 조회
        model.addAttribute("shippingDTO", shippingService.readAll(mid));

//        // 배송 모달창 1개 조회 -> 사용 안 함
//        model.addAttribute("dto", shippingService.readOne(sno));
    }

    // 배송지 관리 -> 모달창에 입력한 값 DB에 저장
    @PostMapping("/shipping")
    public String shippingPost(@Valid ShippingDTO shippingDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("BoardController.registerPost() 배송지 등록");

        // BindingResult는 파라미터에서 @Valid 객체 바로 뒤에 선언되어야 함
        // 잘못된 입력값을 보냈을 시 검증 대상 객체(target)와 검증 결과(errors)에 대한 정보를 담고있는 BindingResult 객체 생성
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/mypage/shipping";
        }

        log.info(shippingDTO);
        log.info("등록 전 sno : " + shippingDTO.getSno());

        // 등록
        if(shippingDTO.getSno() == null) {

            // 새로 등록되는 배송지가 기본배송지가 될 경우 기존 배송지를 기본 배송지에서 해제
            if(shippingDTO.getSdefault() != null) {

                log.info("배송지 추가 회원 mid " + shippingDTO.getMid());

                shippingService.modifySdefault(shippingDTO.getMid());
            }

            Long sno = shippingService.register(shippingDTO);
            redirectAttributes.addFlashAttribute("result", sno);

            log.info("등록 후 sno : " + sno);

            return "redirect:/mypage/shipping";
        }

        // 수정
        if(shippingDTO.getSno() != null) {

            // 수정되는 배송지가 기본배송지가 될 경우 기존 배송지를 기본 배송지에서 해제
            if(shippingDTO.getSdefault() != null) {

                log.info("배송지 추가 회원 mid " + shippingDTO.getMid());

                shippingService.modifySdefault(shippingDTO.getMid());
            }

            log.info("전달 받은 sno 값 : " + shippingDTO.getSno());
            shippingService.modify(shippingDTO);
            redirectAttributes.addFlashAttribute("result", "modified");
            redirectAttributes.addAttribute("sno", shippingDTO.getSno());

            return "redirect:/mypage/shipping";
        }

        return "redirect:/mypage/shipping";
    }

    // 삭제
    @PostMapping("/removeShipping")
    public String remove(Long sno, RedirectAttributes redirectAttributes) {

        log.info("전송받은 sno 값" + sno);

        shippingService.remove(sno);
        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/mypage/shipping";
    }
}
