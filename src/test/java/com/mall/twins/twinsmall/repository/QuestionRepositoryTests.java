package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.constant.Role;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Question;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class QuestionRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInsert() {
        Member tmember = Member.builder()
                .mid("user2")
                .mname("마민수")
                .memail("user2@aaa.bbb")
                .mpw("1234")
                .mbirth("19900101")
                .mphone("01000000002")
                .role(Role.USER)
                .build();

        IntStream.rangeClosed(11, 100).forEach(i -> {
            Question question = Question.builder()
                    .qtitle("문의글 " + i)
                    .qcontent(i + "번째 문의합니다")
                    .qcate("배송문의")
                    .member(tmember)
                    .build();

            memberRepository.save(tmember);
            questionRepository.save(question);
        });
    }

    @Transactional
    @Test
    public void testSelect() {
        Optional<Question> result = questionRepository.findById(2L);
        Question question = result.orElseThrow();
        System.out.println(question.getCreatedBy());
    }

    @Test
    @Transactional
    public void testPaging() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("qno").descending());
        Page<Question> result = questionRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements());
        log.info("total pages : " + result.getTotalPages());
        log.info("page number : " + result.getNumber());
        log.info("page size : " +result.getSize());

        List<Question> questionList = result.getContent();
        questionList.forEach(question -> log.info(question));
    }

    @Test
    public void testSearch1() {
        Pageable pageable = PageRequest.of(1, 3, Sort.by("qno").descending());
        questionRepository.search1(pageable);
    }

    @Test
    public void testSearchAll() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("qno").descending());
        Page<Question> result = questionRepository.searchAll(types, keyword, pageable);
    }

    @Test
    @Transactional
    public void testSearchAll2() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("qno").descending());
        Page<Question> result = questionRepository.searchAll(types, keyword, pageable);

        log.info("total pages : " + result.getTotalPages());
        log.info("page size : " + result.getSize());
        log.info("page number : " + result.getNumber());
        log.info("prev : " + result.hasPrevious() + " next : " + result.hasNext());
        result.getContent().forEach(question -> log.info(question));
    }

}