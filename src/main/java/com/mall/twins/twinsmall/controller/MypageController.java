package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import com.mall.twins.twinsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class MypageController {

    private final MemberService memberService;

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
}
