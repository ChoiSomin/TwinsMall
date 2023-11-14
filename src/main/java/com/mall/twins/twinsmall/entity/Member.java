package com.mall.twins.twinsmall.entity;

import com.mall.twins.twinsmall.constant.MemberRole;
import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;
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

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;           // 회원 번호

    @Column(nullable = false, length = 1000)
    private String mpw;   // 비밀번호

    private String mname;       // 이름

    @Column(nullable = false, unique = true)
    private String memail;      // 이메일

    private String mbirth;   // 생년월일

    @Column(unique = true)
    private String mphone;      // 전화번호

    private boolean mdel;       // 회원탈퇴

    private boolean msocial;    // 소셜 로그인

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public static Member createMember(MemberJoinDTO memberJoinDto, PasswordEncoder passwordEncoder){

        Member member = Member.builder()
                .mname(memberJoinDto.getMname())
                .mbirth(memberJoinDto.getMbirth())
                .mid(memberJoinDto.getMid())
                .mphone(memberJoinDto.getMphone())
                .memail(memberJoinDto.getMemail())
                .mpw( passwordEncoder.encode( memberJoinDto.getMpw() ) ) // BCryptPasswordEncoder Bean 을 파라미터로 넘겨서 비번을 암호화함
                .role(MemberRole.USER)  // 유저
                //.role(MemberRole.ADMIN)   // 관리자
                .build();

        return member;
    }

    public void update(String mname, String memail, String mbirth, String mphone, String mpw){
        this.mname = mname;
        this.memail = memail;
        this.mbirth = mbirth;
        this.mphone = mphone;
        this.mpw = mpw;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String mpw) {
        this.mpw = mpw;
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

    public void changeBirth(String mbirth) { this.mbirth = mbirth; }

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