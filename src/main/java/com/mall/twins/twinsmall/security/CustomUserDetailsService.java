package com.mall.twins.twinsmall.security;

import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.search.MemberRepository;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService { //실제 로그인 처리를 담당함

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info("loadUserByUsernames : " + username);

        Optional<Member> result = memberRepository.getWithRoles(username);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("username not found...");
        }
        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                    member.getMid(),
                    member.getMpw(),
                    member.getMemail(),
                    member.isMdel(),
                false,
                    member.getRoleset().stream()
                            .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                            .collect(Collectors.toList())
        );

        log.info("memberSecurityDTO");
        log.info(memberSecurityDTO);

        return memberSecurityDTO;
    }//loadUserByUsername

    //UserDetails(Interface) getAuthorities();는 사용자가 가진 모든 인가 정보를 반환해야함




}
