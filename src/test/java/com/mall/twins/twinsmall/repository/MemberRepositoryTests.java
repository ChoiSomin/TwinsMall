package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.constant.Role;
import com.mall.twins.twinsmall.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testCreateAdmin() { // 테스트용 관리자 추가
        Member member = Member.builder()
                .mid("admin1")
                .mname("관리자1")
                .memail("admin@aaa.bbb")
                .mpw("1234")
                .mbirth("19900101")
                .mphone("01000000000")
                .role(Role.ADMIN)
                .build();

        memberRepository.save(member);
    }

    @Test
    public void testCreateMember() { // 테스트용 일반유저 추가
        Member member = Member.builder()
                .mid("user1")
                .mname("김철수")
                .memail("user1@aaa.bbb")
                .mpw("1234")
                .mbirth("19900101")
                .mphone("01000000001")
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }

}
