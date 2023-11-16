package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.config.auth.UserAdapter;
import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import com.mall.twins.twinsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final AuthenticationManager authenticationManager;

    // 회원 정보 조회
    @GetMapping("/read")
    public String read(Principal principal, Model model) {

        log.info("MemberController.read() 회원정보 읽기 페이지");
        log.info("유저 아이디 : " + principal.getName());

        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);

        model.addAttribute("member", member);

        return "member/mypage";
    }

    @GetMapping("/modify")
    public String modify(Principal principal, Model model) {

        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);

        model.addAttribute("member", member);

        return "member/modify";
    }

    /**
     * 회원 수정하기 전 비밀번호 확인
     **/
    @GetMapping("/checkPwd")
    public String checkPwdView() {
        return "member/check-pwd";
    }

    @GetMapping("/check")
    @ResponseBody
    public boolean checkPassword(@AuthenticationPrincipal UserAdapter user, @RequestParam String checkPassword, Model model) {
        log.info("checkPwd 진입");

        if (user == null) {
            log.info("user가 존재하지 않음");
            return false;
        }

        String mid = user.getMember().getMid();

        return memberService.checkPassword(mid, checkPassword);
    }

    /**
     * 회원 정보 수정
     **/
    @PutMapping("/modify")
    @ResponseBody
    public boolean update(@RequestBody MemberJoinDTO dto) {

        log.info("MemberRestController 진입");

        if (memberService.checkEmail(dto.getMid(), dto.getMemail())) {
            log.info("중복 이메일");
            return false;
        } else if (memberService.checkPhone(dto.getMid(), dto.getMphone())) {
            log.info("중복 전화번호");
        } else {
            log.info("사용 가능");
        }

        log.info("dto : " + dto);
        // 회원 정보 수정
        memberService.userInfoUpdate(dto);

        /** ========== 변경된 세션 등록 ========== **/
        /* 1. 새로운 UsernamePasswordAuthenticationToken 생성하여 AuthenticationManager 을 이용해 등록 */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getMid(), dto.getMpw())
        );

        /* 2. SecurityContextHolder 안에 있는 Context를 호출해 변경된 Authentication으로 설정 */
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    /* 회원탈퇴 */
    @PostMapping("/delete")
    public String memberDelete(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        boolean result = memberService.withdrawal(userDetails.getUsername());

        if (result) {
            SecurityContextHolder.clearContext(); // 탈퇴 후 시큐리티 clear
            return "redirect:/index";
        } else {
            model.addAttribute("wrongPassword", "비밀번호가 맞지 않습니다.");
            return "/member/withdrawal";
        }
    }
}

