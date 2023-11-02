package com.mall.twins.twinsmall.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {

    private String mid;
    private String mpw;
    private String memail;
    private String mphone;
    private boolean mdel;
    private boolean msocial;

}
