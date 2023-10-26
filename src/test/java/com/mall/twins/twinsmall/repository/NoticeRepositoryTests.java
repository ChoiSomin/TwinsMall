package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class NoticeRepositoryTests {

    @Autowired
    private NoticeRepository noticeRepository;


    @Test
    public void insertNotice() {//공지사항 추가

        IntStream.rangeClosed(1, 50).forEach(i -> {

            Member member = Member.builder()
                    .mno(1L)
                    .build();

            Notice notice = Notice.builder()
                    .ntitle("공지사항 "+i+"번")
                    .ncontent("공지사항 더미데이더 추가 테스트"+i+"번째")
                    .member(member)
                    .build();

            noticeRepository.save(notice);
        });

    }


    @Transactional //로그인 구현 전 강제로 세션 엶
    @Test
    public void testRead(){

        Optional<Notice> result = noticeRepository.findById(1L);

        Notice notice = result.get();

        System.out.println(notice);
        System.out.println(notice.getMember());
    }


    @Test
    public void testReadWithMember(){

        Object result = noticeRepository.getNoticeWithMember(1L);

        Object[] arr = (Object[]) result;

        System.out.println("-------------------------------------");
        System.out.println(Arrays.toString(arr));
    }
}
