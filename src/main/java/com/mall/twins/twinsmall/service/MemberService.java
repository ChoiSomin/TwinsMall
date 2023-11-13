package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;

public interface MemberService {

    static class MidExistException extends Exception {

    }

    Member saveMember(Member member);

    void modify(MemberJoinDTO memberJoinDTO);

    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
