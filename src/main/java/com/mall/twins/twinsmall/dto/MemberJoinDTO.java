package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.MemberRole;
import com.mall.twins.twinsmall.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MemberJoinDTO {

    @NotEmpty (message = "아이디는 필수 입력 값입니다.")
    @Size(min = 6, max = 16, message = "아이디는 6자 이상 16자 이하로 입력해주세요.")
    private String mid;

    private Long mno;

    @NotEmpty (message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문, 숫자, 특수기호가 1개 이상씩 포함된 8자 ~ 20자여야 합니다.")
    private String mpw;

    /*@NotEmpty(message = "비밀번호를 입력해주세요.")
    private String mpw_confirm;*/

    @NotEmpty (message = "이름은 필수 입력 값입니다.")
    private String mname;

    @NotEmpty (message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다")
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
