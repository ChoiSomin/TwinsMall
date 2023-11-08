package com.mall.twins.twinsmall.security;

import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 로그인 인증 처리할 때 호출 : 비밀번호 검사, 사용자 권한 확인 등
    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {

        log.info("loadUserByUsername() 사용자 아이디 : " + mid);

        Optional<Member> result = memberRepository.getWithRoles(mid);

        if(result.isEmpty()) {
            throw new UsernameNotFoundException("해당 아이디를 가진 유저가 존재하지 않습니다.");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getMid(),
                member.getMpw(),
                member.getMname(),
                member.getMemail(),
                member.getMbirth(),
                member.getMphone(),
                member.isMdel(),
                false,
                member.getRoleSet().stream()
                        .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList())
        );

        log.info("memberSecurityDTO");
        log.info(memberSecurityDTO);

        return memberSecurityDTO;
    }
}
