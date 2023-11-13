package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.constant.MemberRole;
import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.MetaMessage;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {

        String mid = memberJoinDTO.getMid();

        // 같은 아이디가 존재하면 예외 발생 처리
        boolean exist = memberRepository.existsById(mid);

        if (exist) {
            throw new MidExistException();
        }

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        member.addRole(MemberRole.USER);

        log.info("====== MemberServiceImpl 클래스 member 로그 출력 ======");
        log.info(member);
        log.info(member.getRoleSet());

        memberRepository.save(member);
    }

    @Override
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public void modify(MemberJoinDTO memberJoinDTO) {

        Optional<Member> result = memberRepository.findById(memberJoinDTO.getMid());

        if(result.isPresent()){

            Member member = result.get();

            member.changeName(memberJoinDTO.getMname());
            member.changeEmail(memberJoinDTO.getMemail());
            member.changePhone(memberJoinDTO.getMphone());
            member.changeBirth(memberJoinDTO.getMbirth());

            memberRepository.save(member);
        }

    } // modify 구현

    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByMid(member.getMid());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        } // 이미 가입된 회원의 경우 IllegalStateException 예외를 발생
    }
}
