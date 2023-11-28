package com.mall.twins.twinsmall.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnswerFromDto {

    private Long ano;

    @NotBlank(message = "내용을 입력하여 주십시오")
    private String acontent;

}
