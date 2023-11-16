package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.entity.Member;
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
}
