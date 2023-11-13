package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import com.mall.twins.twinsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/mypage")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 조회 가능 -> 비로그인 상태일 경우 로그인 화면으로 이동
public class MypageController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    // 회원 정보 조회
    @GetMapping("/read")
    public String read(Principal principal, Model model){

        log.info("MemberController.read() 회원정보 읽기 페이지");
        log.info("유저 아이디 : " + principal.getName());

        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);

        model.addAttribute("member", member);

        return "member/mypage";
    }

    @GetMapping("/modify")
    public String modify(Principal principal, Model model){

        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);

        model.addAttribute("member", member);

        return "member/modify";
    }

    @PostMapping("/modify")
    public String modify(MemberJoinDTO memberJoinDTO, Model model){


        log.info("post modify.........................................");

        memberService.modify(memberJoinDTO);

        model.addAttribute("mid",memberJoinDTO.getMid());

        log.debug("modify method called!"); // 추가

        return "mypage/read";

    }
}
