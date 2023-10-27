package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.entity.Notice;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class NoticeFormDto {

    private Long nno;

    @NotBlank(message = "제목을 입력하여 주십시오")
    private String ntitle; // 제목

    @NotBlank(message = "내용을 입력하여 주십시오")
    private String ncontent; // 내용

    private List<NoticeImgDto> noticeImgDtoList = new ArrayList<>();

    private List<Long> noticeImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Notice cerateNotice(){
        return modelMapper.map(this, Notice.class);
    }

    public static NoticeFormDto of(Notice notice){
        return modelMapper.map(notice, NoticeFormDto.class);
    }





}
