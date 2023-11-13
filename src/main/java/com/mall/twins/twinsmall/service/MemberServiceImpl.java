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
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByMid(member.getMid());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        } // 이미 가입된 회원의 경우 IllegalStateException 예외를 발생
    }

    /**
     * 비밀번호 일치 확인
     **/
    @Override
    public boolean checkPassword(String mid, String checkPassword) {

        Member member = memberRepository.findById(mid).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        /* DB에 저장되어있는 암호화된 비밀번호 */
        String realPassword = member.getMpw();

        /* 입력한 비밀번호와 암호화되어 저장되어있는 비밀번호가 일치하는지 확인 */
        boolean matches = passwordEncoder.matches(checkPassword, realPassword);

        return matches;
    }

    /**
     * 이메일 중복 체크
     **/
    @Override
    public boolean checkEmail(String mid, String memail) {

        Optional<Member> optionalMember = memberRepository.findByMemail(memail);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            if (memberRepository.existsByEmail(memail)) {

                if (member.getMid() == mid) {
                    // 입력 받은 이메일의 회원 id와 일치한다면 즉, 현재 이메일을 그대로 입력한 경우
                    return false;
                } else {
                    // 다른 사람이 사용하고 있는 이메일이라면
                    return true;
                }
            } else {
                // 중복된 이메일이 아니라면
                return false;
            }
        }
        return false;
    }

    /**
     * 닉네임 중복 체크
     **/
    @Override
    public boolean checkPhone(String mid, String mphone) {

        if (memberRepository.existsByPhone(mphone)) {
            /*if (memberRepository.existsByPhone(mphone).getId() == member_id) {
                // 입력 받은 닉네임의 회원 id와 일치한다면 즉, 현재 닉네임을 그대로 입력한 경우
                return false;
            } else {
                // 다른 사람이 사용하고 있는 닉네임이라면
                return true;
            }*/
        } else {
            // 중복된 닉네임이 아니라면
            return false;
        }
    }

    /**
     * 회원 수정
     **/
    @Override
    public void userInfoUpdate(MemberJoinDTO memberDto) {

        /* 회원 찾기 *//*
        Member member = memberRepository.findById(memberDto.toEntity().getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        *//* 수정한 비밀번호 암호화 *//*
        String encryptPassword = encoder.encode(memberDto.getPassword());
        member.update(memberDto.getNickname(), encryptPassword); // 회원 수정

        log.info("회원 수정 성공");*/
    }
}
