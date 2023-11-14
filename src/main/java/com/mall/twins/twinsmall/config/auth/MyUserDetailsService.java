package com.mall.twins.twinsmall.config.auth;

import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Log4j2
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    /** username이 DB에 존재하는지 확인 **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByMid(username);

        if (member != null) {
            /** 시큐리티 세션에 유저 정보 저장**/
            return new UserAdapter(member);
        } else {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }
    }
}