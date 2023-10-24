package com.mall.twins.twinsmall.entity;

import com.mall.twins.twinsmall.constant.Role;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(nullable = false, unique = true) // 아이디를 유일하게 구분(동일값이 db에 들어올 수 없음)
    private String mid;

    @Column(nullable = false)
    private String mname;

    @Column(nullable = false, unique = true)
    private String memail;

    @Column(nullable = false)
    private String mpw;

    /*public void changePassword(String mpw){
        this.mpw = mpw;
    }*/

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String mphone; // 전화번호


    private String mbirth; // 생년월일

    /* 추가 */
    private boolean mdel; //탈퇴 여부

    private boolean msocial; //SNS 여부

    @ElementCollection(fetch = FetchType.LAZY)
    //@ElementCollection : 컬렉션 형태의 데이터를 엔티티와 관련시킬 때 사용
    //fetch = FetchType.LAZY : 데이터 지연 로딩
    @Builder.Default
    //@Builder.Default :  Lombok 라이브러리를 사용하는 경우 사용
    private Set<Role> roleset = new HashSet<>();
    //MyEntity 클래스의 빌더를 생성할 때 roleset 초기화하는 기본값이 빈 HashSet으로 설정

    public void changePassword(String mpw){
        this.mpw = mpw;
    }

    public void changeDel(boolean mdel){
        this.mdel = mdel;
    }
    public void addRole(Role role){
        this.roleset.add(role);
    }

    public void changeSocial(boolean msocial){
        this.msocial = msocial;
    }

    /*public static Member createMember(MemberJoinDto memberJoinDto, PasswordEncoder passwordEncoder){

        Member member = Member.builder()
                .name(memberJoinDto.getName())
                .email(memberJoinDto.getEmail())
                .password( passwordEncoder.encode( memberJoinDto.getPassword() ) ) // BCryptPasswordEncoder Bean 을 파라미터로 넘겨서 비번을 암호화함
                .role(Role.USER)  // 유저
                //.role(Role.ADMIN)   // 관리자
                .build();

        return member;
    }*/

}