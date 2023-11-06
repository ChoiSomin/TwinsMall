package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    @GetMapping("/login")
    public void loginGet(String errorCode, String logout) {

        log.info("MemberController.loginGet() 로그인 처리");
        log.info("logout : " + logout);

        if(logout != null) {
            log.info("사용자 로그아웃");
        }
    }

    // 회원가입
    @GetMapping("/join")
    public String joinGet(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDTO());

        log.info("MemberController.joinGet() 회원가입 페이지 접근");

        return "member/join";
    }

    @GetMapping(value = "/members/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/login";
    }

    @PostMapping("/join")
    public String joinPost(@Valid @ModelAttribute("memberJoinDto") MemberJoinDTO memberJoinDto, BindingResult bindingResult, Model model) {

        log.info("MemberController.joinPost() 회원가입 데이터 전송");
        log.info(memberJoinDto);

        if (bindingResult.hasErrors()) {
            log.error("Validation errors detected.");
            return "member/join";
        }

        try {
            Member member = Member.createMember(memberJoinDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            String errorMessage = e.getMessage();
            log.error("Failed to save member: {}", errorMessage);
            model.addAttribute("errorMessage", e.getMessage());

            return "member/join";
        }

        return "index";
    }

}
