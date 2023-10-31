package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void loginGet(String errorCode, String logout) {

        log.info("MemberController.loginGet() 로그인 처리");
        log.info("logout : " + logout);

        if(logout != null) {
            log.info("사용자 로그아웃");
        }
        //로그인 과정에 문제 생기거나 로그아웃 처리 할 때 사용하기 위해
    }

    @PostMapping("/login")
    public String loginPost(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {

        log.info("MemberController.loginPost() 로그인 처리");
        log.info("memberJoinDTO : "+memberJoinDTO);



        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:index";

    }

    @GetMapping("/join")
    public void joinGet() {

        log.info("MemberController.joinGet() 회원가입 페이지 접근");
    }
    
    @PostMapping("/join")
    public String joinPost(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        
        log.info("MemberController.joinPost() 회원가입 데이터 전송");
        log.info(memberJoinDTO);

        try {
            memberService.join(memberJoinDTO);

        } catch (MemberService.MidExistException e) {

            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:member/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:member/login"; //회원가입 후 로그인
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list.............."+pageRequestDTO);

        model.addAttribute("result", memberService.getList(pageRequestDTO));

    }

    // 회원정보 조회
    @PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 조회 가능
    @GetMapping({"/mypage/read", "/mypage/modify"})
    public void read(Principal principal, Model model) {

        log.info("MemberController.read() 회원정보 페이지 접근");
        log.info("유저 아이디 : " + principal.getName());

        String mid = principal.getName();
        MemberSecurityDTO memberSecurityDTO = memberService.readOne(mid);

        model.addAttribute("dto", memberSecurityDTO);
    }

    // 회원정보 조회
    @PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 조회 가능
    @PostMapping("/mypage/read")
    public void postRead(Principal principal, Model model) {

        log.info("MemberController.read() 회원정보 페이지 접근");
        log.info("유저 아이디 : " + principal.getName());

        String mid = principal.getName();
        MemberSecurityDTO memberSecurityDTO = memberService.readOne(mid);

        model.addAttribute("dto", memberSecurityDTO);
    }

    @PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 조회 가능
    @PostMapping( "/mypage/modify")
    public String postModify(MemberSecurityDTO dto, RedirectAttributes redirectAttributes ) {

        log.info("post modify.........................................");
        log.info("dto: " + dto);

        memberService.modify(dto);

        return "redirect:member/mypage/read";

    }

    @PostMapping("mypage/remove")
    public String remove(String mid, RedirectAttributes redirectAttributes) {
        log.info("mid : "+mid);

        memberService.remove(mid);

        redirectAttributes.addFlashAttribute("msg", mid);

        return "redirect:index";
    }

    @GetMapping("/logout")
    public String logoutMaineGET(HttpServletRequest request)throws Exception{
        log.info("logoutMainGet 메서드 진입");

        HttpSession session = request.getSession();

        session.invalidate();
        return "redirect:index";
    }

}
