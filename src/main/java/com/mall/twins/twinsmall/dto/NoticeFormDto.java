package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.entity.Notice;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFormDto{

    private Long nno;

    @NotBlank(message = "제목을 입력하여 주십시오")
    private String ntitle; // 제목

    @NotBlank(message = "내용을 입력하여 주십시오")
    private String ncontent; // 내용

    private LocalDateTime regTime;    // 등록일
    private LocalDateTime updateTime; // 수정일

    private int view; // 조회수

    public static NoticeFormDto of(Notice notice) {
        return NoticeFormDto.builder()
                .nno(notice.getNno())
                .ntitle(notice.getNtitle())
                .ncontent(notice.getNcontent())
                .regTime(notice.getRegTime())
                .updateTime(notice.getUpdateTime())
                .view(notice.getView())
                .build();
    }

    /*private static ModelMapper modelMapper = new ModelMapper();

    public NoticeFormDto() {

    }

    public Notice createNotice(){
        return modelMapper.map(this, Notice.class);
    }

    public static NoticeFormDto of(Notice notice){
        return modelMapper.map(notice, NoticeFormDto.class);
    }

    @QueryProjection // querydsl로 결과 조회 시 NoticeFormDto 객체로 바로 받아옴
    public NoticeFormDto(Long nno, String ntitle, String ncontent, LocalDateTime regTime, LocalDateTime updateTime, int view){
        this.nno = nno;
        this.ntitle = ntitle;
        this.ncontent = ncontent;
        this.regTime = regTime;
        this.updateTime = updateTime;
        this.view = view;
    }*/



}
