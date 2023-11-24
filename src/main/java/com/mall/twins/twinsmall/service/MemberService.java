package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;

public interface MemberService {

    static class MidExistException extends Exception {

    }

    Member saveMember(Member member);

    /** 비밀번호 일치 확인 **/
    boolean checkPassword(String mid, String checkPassword);

    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;

    /** 회원 수정 **/
    void userInfoUpdate(MemberJoinDTO dto);

    /** 이메일 중복 체크 **/
    boolean checkEmail(String mid, String memail);

    /** 전화번호 중복 체크 **/
    boolean checkPhone(String mid, String mphone);

    /** 회원탈퇴 **/
    boolean withdrawal(String mid);

    String get_searchId(String mname, String mphone);

    PageResultDTO<MemberJoinDTO, Member> getMemberList(PageRequestDTO pageRequestDTO);

    default MemberJoinDTO entityToDTO(Member member) {

        MemberJoinDTO memberJoinDTO = MemberJoinDTO.builder()
                .mno(member.getMno())
                .mid(member.getMid())
                .mbirth(member.getMbirth())
                .mphone(member.getMphone())
                .mname(member.getMname())
                .mpw(member.getMpw())
                .memail(member.getMemail())
                .regTime(member.getRegTime())
                .build();

        return memberJoinDTO;

    }
}
