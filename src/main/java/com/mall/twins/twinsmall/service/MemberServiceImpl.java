package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.constant.Role;
import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException{

        String mid = memberJoinDTO.getMid();
        boolean exist = memberRepository.existsById(mid);

        if(exist){
            throw new MidExistException();
        }
            Member member = modelMapper.map(memberJoinDTO, Member.class);
            member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
            member.addRole(Role.USER);

            log.info("==================================");
            log.info(member);
            log.info(member.getRoleset());

            memberRepository.save(member);
    }

}
