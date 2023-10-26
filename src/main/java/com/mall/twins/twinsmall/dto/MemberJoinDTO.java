package com.mall.twins.twinsmall.dto;

import lombok.Data;

@Data
public class MemberJoinDTO { //회원가입을 위한 DTO

    private Long mno;
    private String mid;
    private String mpw;
    private String memail;
    private boolean mdel;
    private boolean msocial;
}
