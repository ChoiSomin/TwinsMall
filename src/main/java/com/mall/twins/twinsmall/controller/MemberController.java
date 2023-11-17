package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.service.MemberServiceImpl;
import com.mall.twins.twinsmall.service.validator.CheckPasswordEqualValidator;
import com.mall.twins.twinsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberServiceImpl memberServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final CheckPasswordEqualValidator checkPasswordEqualValidator;



    @GetMapping("/login")
    public void loginGet(String errorCode, String logout) {

        log.info("MemberController.loginGet() 로그인 처리");
        log.info("logout : " + logout);

        if(logout != null) {
            log.info("사용자 로그아웃");
        }
    }

    @GetMapping("/join")
    public String joinGet(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDTO());

        log.info("MemberController.joinGet() 회원가입 페이지 접근");

        return "member/join";
    }

    @GetMapping(value = "/login/error")
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

        if (bindingResult.hasErrors()) {
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberServiceImpl.validateHandling(bindingResult);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            // 회원가입 페이지로 다시 리턴
            return "member/join";
        }

        try {
            Member member = Member.createMember(memberJoinDto, passwordEncoder);
            memberService.saveMember(member);
            String successMessage = member.getMname() + "님, 가입을 축하드립니다!";
            model.addAttribute("successMessage", successMessage);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            log.error("Failed to save member: {}", errorMessage);
            model.addAttribute("errorMessage", e.getMessage());

            return "member/join";
        }

        return "index";
    }

    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkPasswordEqualValidator);
    }

    /* ID/PW 찾기 */
    @GetMapping("/find-IdPw")
    public String find(){
        return "member/find-IdPw";
    }

    // 아이디 찾기
    @RequestMapping(value = "/member/IdSearch", method = RequestMethod.POST)
    @ResponseBody
    public String userIdSearch(@RequestParam("inputName_1") String mname,
                               @RequestParam("inputPhone_1") String mphone) {

        String result = memberService.get_searchId(mname, mphone);

        return result;
    }


}
