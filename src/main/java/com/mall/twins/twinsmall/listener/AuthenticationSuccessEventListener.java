package com.mall.twins.twinsmall.listener;

import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent){
        Member member = (Member) authenticationSuccessEvent.getAuthentication().getPrincipal(); //로그인에 성공하면 사용자의 인증 정보를 추출
        loginAttemptService.loginSuccess(member.getMid()); //loginSuccess() 메소드를 호출해서 해당 사용자의 로그인 실패 횟수를 캐시에서 무효화(삭제)
    }
}
