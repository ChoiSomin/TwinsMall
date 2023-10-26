package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.MemberDTO;
import com.mall.twins.twinsmall.dto.QuestionDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Question;
import com.mall.twins.twinsmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @Override
    public Long register(MemberDTO memberDTO) {
        Member member = modelMapper.map(memberDTO, Member.class);
        Long mno = memberRepository.save(member).getMno();
        return mno;
    }

    @Override
    public MemberDTO readOne(Long mno) {
        Optional<Member> result = memberRepository.findById(mno);
        Member member = result.orElseThrow();
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        return memberDTO;
    }

    @Override
    public Member readEntity(Long mno) {
        Optional<Member> result = memberRepository.findById(mno);
        Member member = result.orElseThrow();
        return member;
    }

}
