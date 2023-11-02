package com.mall.twins.twinsmall.security;

import com.mall.twins.twinsmall.constant.MemberRole;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("CustomOAuth2UserService.loadUser() 카카오 소셜 로그인 서비스");
        log.info(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();
        log.info("Client Name : " + clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String memail = null;

        switch (clientName) {
            case "kakao":
                memail = getKaKaoEmail(paramMap);
                break;
        }

        log.info("소셜 로그인 이메일 주소" + memail);

        return generateDTO(memail, paramMap);
    }

    private MemberSecurityDTO generateDTO(String memail, Map<String, Object> params) {

        // 이메일로 회원 정보 조회
        Optional<Member> result = memberRepository.findByMemail(memail);

        // 데이터베이스에 해당 이메일의 사용자가 없다면
        if(result.isEmpty()) {
            // 회원 추가
            Member member = Member.builder()
                    .mid(memail)
                    .mpassword(passwordEncoder.encode("1111"))
                    .memail(memail)
                    .msocial(true)
                    .build();

            member.addRole(MemberRole.USER);
            memberRepository.save(member);

            MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(memail, "1111", null,
                    memail, null, null, false, true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

            memberSecurityDTO.setProps(params);

            return memberSecurityDTO;

        } else {

            Member member = result.get();

            MemberSecurityDTO memberSecurityDTO =
                    new MemberSecurityDTO(
                            member.getMid(),
                            member.getMpassword(),
                            member.getMname(),
                            member.getMemail(),
                            member.getMbirthday(),
                            member.getMphone(),
                            member.isMdel(),
                            member.isMsocial(),
                            member.getRoleSet()
                                    .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                                    .collect(Collectors.toList())
                    );

            return memberSecurityDTO;
        }
    }

    private String getKaKaoEmail(Map<String, Object> paramMap) {

        log.info("Kakao 로그인 이메일 정보");

        Object value = paramMap.get("kakao_account");
        log.info("kakao_account : " + value);

        LinkedHashMap accountMap = (LinkedHashMap) value;

        String memail = (String) accountMap.get("email");
        log.info("카카오 소셜로그인 이메일 주소 : " + memail);

        return memail;
    }
}
