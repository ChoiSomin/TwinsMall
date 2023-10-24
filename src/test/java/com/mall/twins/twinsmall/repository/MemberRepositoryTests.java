package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.constant.Role;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.search.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers(){

        IntStream.rangeClosed(100, 199).forEach(i -> {

            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .mname("name"+ i)
                    .memail("email" + i + "@email.com")
                    .mphone("010"+i+"00000")
                    .build();

            member.addRole(Role.USER);

            if(i >= 190){
                member.addRole(Role.ADMIN);
            }
            memberRepository.save(member);
        });
    }//insertMembers
    
    @Test
    public void testRead(){
        
        Optional<Member> result = memberRepository.getWithRoles("member195");
        
        Member member = result.orElseThrow();
        
        log.info(member);
        log.info(member.getRoleset());
        
        member.getRoleset().forEach(Role -> log.info(Role.name()));
    }
}
