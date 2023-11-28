package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.entity.Question;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionFormDto {

    private Long qno;

    @NotBlank(message = "제목을 입력하여 주십시오")
    private String qtitle; // 제목

    @NotBlank(message = "내용을 입력하여 주십시오")
    private String qcontent; // 내용

    private String qcate;

    private static ModelMapper modelMapper = new ModelMapper();

    public Question cerateQuestion(){
        return modelMapper.map(this, Question.class);
    }

    public static QuestionFormDto of(Question question){
        return modelMapper.map(question, QuestionFormDto.class);
    }



}
