package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
public class MemberDto {

    private Long mno;

    private String mid;

    private String mname;

    private String memail;

    private String mpw;

    private Role role;

    private String mphone; // 전화번호

    private String mbirth; // 생년월일
}
