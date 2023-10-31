package com.mall.twins.twinsmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberJoinDTO {

    private String mid;

    private String mpw;

    private String mname;

    private String memail;

    private String mbirth;

    private String mphone;

    private boolean mdel;

    private boolean msocial;
}
