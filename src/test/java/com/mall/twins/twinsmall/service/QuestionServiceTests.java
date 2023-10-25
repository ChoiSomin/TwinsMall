package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.constant.Role;
import com.mall.twins.twinsmall.dto.QuestionDTO;
import com.mall.twins.twinsmall.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class QuestionServiceTests {

    @Autowired
    private QuestionService questionService;

    @Test
    public void testRegister() {
        log.info(questionService.getClass().getName());

        Member tmember = Member.builder()
                .mid("user2")
                .mname("마민수")
                .memail("user2@aaa.bbb")
                .mpw("1234")
                .mbirth("19900101")
                .mphone("01000000002")
                .role(Role.USER)
                .build();

        QuestionDTO questionDTO = QuestionDTO.builder()
                .qtitle("문의합니다")
                .qcontent("문의 내용입니다")
                .qcate("배송문의")
                .member(tmember)
                .build();

        Long qno = questionService.register(questionDTO);
        log.info("qno : " + qno);
    }

}
