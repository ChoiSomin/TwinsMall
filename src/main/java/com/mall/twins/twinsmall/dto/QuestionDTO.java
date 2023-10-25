package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Long qno; // 질문 번호
    private String qtitle; // 질문 제목
    private String qcontent; // 질문내용
    private String qcate; // 카테고리
    private Member member; // 작성자
    private LocalDateTime regDate; // 등록 시간
    private LocalDateTime updateTime; // 수정 시간
    private String createdBy; // 등록자
    private String modifiedBy; // 수정자

}
