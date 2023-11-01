package com.mall.twins.twinsmall.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MemberJoinDTO {

    @NotEmpty
    private String mid;

    @NotEmpty
    private String mpassword;

    private String mname;

    @NotEmpty
    private String memail;

    private String mbirthday;

    private String mphone;

    private boolean mdel;

    private boolean msocial;
}
