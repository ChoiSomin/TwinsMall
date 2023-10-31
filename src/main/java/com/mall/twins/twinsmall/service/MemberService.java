package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;


public interface MemberService {

    static class MidExistException extends Exception {

    }

    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;

    PageResultDTO<MemberJoinDTO, Member> getList(PageRequestDTO requestDTO);

    default Member dtoToEntity(MemberJoinDTO dto) {
        Member entity = Member.builder()
                .mid(dto.getMid())

                .mbirth(dto.getMbirth())
                .memail(dto.getMemail())
                .mname(dto.getMname())
                .mpw(dto.getMpw())
                .mphone(dto.getMphone())
                .build();
        return entity;
    }

    default MemberJoinDTO entityToDto(Member entity) {

        MemberJoinDTO dto = MemberJoinDTO.builder()
                .mid(entity.getMid())

                .mbirth(entity.getMbirth())
                .memail(entity.getMemail())
                .mname(entity.getMname())
                .mpw(entity.getMpw())
                .mphone(entity.getMphone())
                .build();

        return dto;
    }

    MemberSecurityDTO readOne(String id);

    MemberSecurityDTO modify(MemberSecurityDTO memberSecurityDTO);

    void remove(String mid);
}

