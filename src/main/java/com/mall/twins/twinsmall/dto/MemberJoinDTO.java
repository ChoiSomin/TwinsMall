package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.MemberRole;
import com.mall.twins.twinsmall.entity.Member;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MemberJoinDTO {

    @NotEmpty (message = "아이디는 필수 입력 값입니다.")
    private String mid;

    private Long mno;

    @NotEmpty (message = "비밀번호는 필수 입력 값입니다.")
    private String mpw;

    @NotEmpty (message = "이름은 필수 입력 값입니다.")
    private String mname;

    @NotEmpty (message = "이메일은 필수 입력 값입니다.")
    private String memail;

    @NotEmpty (message = "생년월일은 필수 입력 값입니다.")
    private String mbirth;

    @NotEmpty (message = "전화번호는 필수 입력 값입니다.")
    private String mphone;

    private boolean mdel;

    private boolean msocial;

    /** 암호화된 password **/
    public void encryptPassword(String BCryptpassword){
        this.mpw = BCryptpassword;
    }

    public Member toEntity(){
        Member member = Member.builder()
                .mid(mid)
                .mname(mname)
                .memail(memail)
                .mbirth(mbirth)
                .mphone(mphone)
                .role(MemberRole.USER)
                .build();

        return member;
    }
}
