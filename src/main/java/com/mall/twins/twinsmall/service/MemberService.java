package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;

public interface MemberService {

    static class MidExistException extends Exception {

    }

    // 회원가입
    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;

    // 조회
    MemberSecurityDTO readOne(String id);

    // 수정
    void modify(MemberSecurityDTO memberSecurityDTO);
}
