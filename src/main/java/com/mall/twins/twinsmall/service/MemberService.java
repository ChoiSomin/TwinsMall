package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception{

    }

    void join(MemberJoinDTO memberJoinDTO)throws MidExistException; //MemberServiceImpl에서 mid가 존재하는 경우 MidExistException 발생
}
