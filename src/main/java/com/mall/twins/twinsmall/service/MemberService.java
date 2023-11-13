package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.entity.Member;

public interface MemberService {

    static class MidExistException extends Exception {

    }

    Member saveMember(Member member);


    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;

}
