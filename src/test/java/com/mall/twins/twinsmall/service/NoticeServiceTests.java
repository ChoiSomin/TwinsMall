package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.NoticeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class NoticeServiceTests {

    @Autowired
    private NoticeService noticeService;

    /*@Transactional*/
    @Test
    public void testRegister(){

        NoticeDTO dto = NoticeDTO.builder()
                .ntitle("register Test")
                .ncontent("공지사항 등록 테스트")
                .member("admin")
                .build();

        Long nno = noticeService.register(dto);
    }
}
