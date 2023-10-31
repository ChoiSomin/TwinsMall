package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.constant.MemberRole;
import com.mall.twins.twinsmall.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Test
//    public void insertMembers() {
//        IntStream.rangeClosed(1,100).forEach(i -> {
//            Member member = Member.builder()
//                    .memail("cs"+i+"@eunhaimart.com")
//                    .mpassword("1111")
//                    .mname("춘식이 "+i+"번").build();
//            memberRepository.save(member);
//        });
//    }

    /* 일반 회원 추가 */
    @Test
    public void insertMembers() {

        IntStream.rangeClosed(100,110).forEach(i -> {
            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .mname("name" + i)
                    .memail("email" + i + "@aaa.bbb")
                    .mphone("0100"+i+"0000")
                    .build();

            member.addRole(MemberRole.USER);

            if(i >= 108) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

    @Test
    public void testRead() {

        Optional<Member> result = memberRepository.getWithRoles("member108");
        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }

    @Test
    @Commit
    public void testUpdate() {

        String mid = "goflvnej@naver.com";
        String mpw = passwordEncoder.encode("54321");

        memberRepository.updatePassword(mid, mpw);
    }
}
