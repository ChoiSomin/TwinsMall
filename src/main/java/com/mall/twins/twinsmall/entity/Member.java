package com.mall.twins.twinsmall.entity;

import com.mall.twins.twinsmall.constant.Role;
import lombok.*;

import javax.persistence.*;

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