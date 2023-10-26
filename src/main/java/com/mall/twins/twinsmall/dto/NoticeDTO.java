package com.mall.twins.twinsmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeDTO {

        //공지사항 게시글 번호
        private Long nno;

        //공지사항 제목
        private String ntitle;

        //공지사항 내용
        private String ncontent;

        //작성자(admin)
        private String member;

        //BaseTimeEntity의 작성시간, 수정시간
        private LocalDateTime regTime, updateTime;

}
