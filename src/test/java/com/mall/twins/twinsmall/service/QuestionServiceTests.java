package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResponseDTO;
import com.mall.twins.twinsmall.dto.QuestionDTO;
import com.mall.twins.twinsmall.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class QuestionServiceTests {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private MemberService memberService; // 테스트용 임시 서비스

    @Test
    public void testRegister() { // 문의글 등록 테스트
        log.info(questionService.getClass().getName());

        Member tmember = memberService.readEntity(6L);

        QuestionDTO questionDTO = QuestionDTO.builder()
                .qtitle("문의문의합니다")
                .qcontent("문의 내용입니다")
                .qcate("배송문의")
                .member(tmember)
                .build();

        Long qno = questionService.register(questionDTO);
        log.info("qno : " + qno);

    }

    @Test
    @Transactional
    public void testList() { // 목록 검색 테스트
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                                                        .type("tcw")
                                                        .keyword("1")
                                                        .page(1)
                                                        .size(10)
                                                        .build();
        PageResponseDTO<QuestionDTO> pageResponseDTO = questionService.list(pageRequestDTO);
        log.info(pageResponseDTO);
    }

}
