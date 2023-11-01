package com.mall.twins.twinsmall.entity;

import com.mall.twins.twinsmall.constant.MemberRole;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String mid;         // 아이디

    @Column(nullable = false, length = 1000)
    private String mpassword;   // 비밀번호

    private String mname;       // 이름

    @Column(nullable = false, unique = true)
    private String memail;      // 이메일

    private String mbirthday;   // 생년월일

    @Column(unique = true)
    private String mphone;      // 전화번호

    private boolean mdel;       // 회원탈퇴

    private boolean msocial;    // 소셜 로그인

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String mpassword) {
        this.mpassword = mpassword;
    }

    public void changeName(String mname) {
        this.mname = mname;
    }

    public void changeEmail(String memail) {
        this.memail = memail;
    }

    public void changePhone(String mphone) {
        this.mphone = mphone;
    }

    public void changeDel(boolean mdel) {
        this.mdel = mdel;
    }

    public void addRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }

    public void changeSocial(boolean msocial) {
        this.msocial = msocial;
    }

}