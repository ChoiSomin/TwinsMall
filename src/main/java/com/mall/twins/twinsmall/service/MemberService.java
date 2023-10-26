package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberDTO;
import com.mall.twins.twinsmall.dto.QuestionDTO;
import com.mall.twins.twinsmall.entity.Member;

public interface MemberService {

    Long register(MemberDTO memberDTO); // 가입
    MemberDTO readOne(Long mno); // 멤버 상세보기
    Member readEntity(Long mno); // 멤버 Entity 가지고오기

}
