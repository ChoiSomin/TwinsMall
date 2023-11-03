package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public void loginGET(String error, String logout){
        log.info("===========login get===========");
        log.info("logout : " + logout);

        if(logout != null){
            log.info("============user logout=========="); //로그아웃 여부확인
        }
    }//loginGET

    @GetMapping("/join")
    public void joinGET(){
        log.info("==============join get================");
    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO){ //회원가입 폼에서 post 처리 후 list로 리다이렉트

        log.info("===========join post=========");
        log.info(memberJoinDTO);

        return "redirect:/board/list"; //***********수정
    }

    @GetMapping("/login-locked")
    public String loginLocked(Model model){
        model.addAttribute("loginLocked", true); //loginLocked 플래그값을 true로 설정해서 계정 잠김 메시지를 화면에 표시하는
        return "member/login";
    }
}
