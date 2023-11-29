package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.config.auth.CustomUserDetails;
import com.mall.twins.twinsmall.config.auth.UserAdapter;
import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.dto.ShippingDto;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.repository.ShippingRepository;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import com.mall.twins.twinsmall.service.MemberService;
import com.mall.twins.twinsmall.service.ShippingService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/mypage")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 조회 가능 -> 비로그인 상태일 경우 로그인 화면으로 이동
public class MypageController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final ShippingService shippingService;

    private final ShippingRepository shippingRepository;

    @GetMapping("/shipping/list")
    public String list(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String mid = userDetails.getUsername();

        model.addAttribute("shippingDTO", shippingService.readAll(mid));

        log.info("shippingService.readAll(mid)");

        return "shipping/shipping";
    }

    @GetMapping("/shipping/register")
    public String shippingRegister(Model model) {

        model.addAttribute("shippingDto", new ShippingDto());
        log.info("register");
        return "shipping/shippingRegister";
    }


    @PostMapping(value = "/shipping/register")
    public String shippingRegister(@Valid ShippingDto shippingDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        log.info("배송지 등록");

        String loggedId = principal.getName();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "/mypage/shipping/list";
        }

        shippingDto.setMid(loggedId);

        Long sno = shippingService.register(shippingDto);
        redirectAttributes.addFlashAttribute("result", sno);

        log.info(sno);

        return "/mypage/shipping/list";
    }


    @GetMapping("/shipping/read{sno}")
    public String read(long sno, Model model){

        ShippingDto shippingDto = shippingService.readOne(sno);

        model.addAttribute("shippingDto", shippingDto);


        return "shipping/shippingRead";
    }

    @GetMapping("/shipping/modify")
    public String Modifyread(long sno, Model model){

        ShippingDto shippingDto = shippingService.readOne(sno);

        model.addAttribute("shippingDto", shippingDto);


        return "mypage/shippingModify";
    }

    @PostMapping("/shipping/modify")
    public String shippingModify(ShippingDto shippingDto, RedirectAttributes redirectAttributes) {
        log.info("shipping modify");
        log.info("shippingDTO: " + shippingDto);

        shippingService.modify(shippingDto);

        // 'sno' 값을 RedirectAttributes에 추가하여 URL에 해당 값을 전달
        redirectAttributes.addAttribute("sno", shippingDto.getSno());

        // Redirect 시, URL에 'sno' 값을 포함하여 리다이렉트합니다.
        return "redirect:/shipping/shipping/read";
    }

    @PostMapping("/shipping/remove")
    public String remove(@RequestParam("sno") long sno, RedirectAttributes redirectAttributes){

        log.info("sno" + sno);

        shippingService.remove(sno);

        redirectAttributes.addFlashAttribute("msg", sno);

        return "redirect:/shipping/shipping/list";
    }

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
    @PostMapping("/modify")
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
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) currentAuthentication.getPrincipal();

        // 사용자 정보 업데이트
        userDetails.setUsername(dto.getMid());
        userDetails.setPassword(dto.getMpw());

        // 변경된 Authentication으로 SecurityContextHolder 업데이트
        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(userDetails, dto.getMpw(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

        return true;
    }

    /* 회원탈퇴 */
    @PostMapping("/delete")
    public String memberDelete(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        boolean result = memberService.withdrawal(userDetails.getUsername());

        if (result) {
            SecurityContextHolder.clearContext(); // 탈퇴 후 시큐리티 clear
            return "redirect:/";
        } else {
            model.addAttribute("wrongPassword", "비밀번호가 맞지 않습니다.");
            return "/member/withdrawal";
        }
    }
}

