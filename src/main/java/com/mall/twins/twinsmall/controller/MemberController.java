package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
//@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public void loginGET(String error, String logout){
        //error, logout : 로그인 과정에 문제가 생기거나 로그아웃 처리할 때 사용하기 위함(데이터 베이스 처리시 사용)

        log.info("============login get============");
        log.info("logout : " + logout);

        if(logout != null){
            log.info("==============user logout=========");
        }
    }//loginGET

    /* 회원가입 */
    @GetMapping("/join")
    public void joinGet(){

        log.info("============joing get============");
    }//joinGet

    @PostMapping("/join")
    public String joingPOST(MemberJoinDTO memberJoinDTO){

        log.info("============join post============");
        log.info(memberJoinDTO);

        return "redirect:/index";
    }

}
