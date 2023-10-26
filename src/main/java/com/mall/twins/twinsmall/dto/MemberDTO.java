package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long mno;
    private String mid;
    private String mname;
    private String memail;
    private String mpw;
    private Role role;
    private String mphone; // 전화번호
    private String mbirth; // 생년월일
    private LocalDateTime regDate; // 가입일
    private LocalDateTime updateTime; // 수정 시간

}
