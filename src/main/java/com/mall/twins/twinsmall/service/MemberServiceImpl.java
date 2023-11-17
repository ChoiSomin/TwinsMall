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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.sound.midi.MetaMessage;
import java.util.HashMap;
import java.util.Map;
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
        Member findMid = memberRepository.findByMid(member.getMid());
        Optional<Member> findMemail = memberRepository.findByMemail(member.getMemail());
        Optional<Member> findMphone = memberRepository.findByMphone(member.getMphone());

        if (findMid != null) { // 이미 가입된 회원의 경우 IllegalStateException 예외를 발생
            throw new IllegalStateException("이미 가입된 회원입니다.");
        } else if (findMemail.isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        } else if (findMphone.isPresent()) {
            throw new IllegalArgumentException("이미 가입된 번호입니다.");
        } else {

        }


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

        log.info(realPassword);

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

        return optionalMember.map(member -> {
            if (member.getMid().equals(mid)) {
                // 입력 받은 이메일의 회원 id와 일치한다면 즉, 현재 이메일을 그대로 입력한 경우
                return false;
            } else {
                // 다른 사람이 사용하고 있는 이메일이라면
                return true;
            }
        }).orElse(false); // 회원이 없는 경우 중복된 것이 아니므로 false 반환
    }

    /**
     * 전화번호 중복 체크
     **/
    @Override
    public boolean checkPhone(String mid, String mphone) {
        Optional<Member> optionalMember = memberRepository.findByMphone(mphone);

        return optionalMember.map(member -> {
            if (member.getMid().equals(mid)) {
                // 입력 받은 이메일의 회원 id와 일치한다면 즉, 현재 이메일을 그대로 입력한 경우
                return false;
            } else {
                // 다른 사람이 사용하고 있는 이메일이라면
                return true;
            }
        }).orElse(false); // 회원이 없는 경우 중복된 것이 아니므로 false 반환
    }


    /**
     * 회원 수정
     **/
    @Transactional
    @Override
    public void userInfoUpdate(MemberJoinDTO dto) {

        /* 회원 찾기 */
        Member member = memberRepository.findById(dto.toEntity().getMid())
                .orElse(null);

        log.info("member : " + member);
        if (member == null) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다.");
        }

        String encryptPassword;

        if(dto.getMpw() != null){
            /* 수정한 비밀번호 암호화 */
            encryptPassword  = passwordEncoder.encode(dto.getMpw());
        } else {
            encryptPassword = member.getMpw();
        }

        log.info(encryptPassword);

        member.update(dto.getMname(), dto.getMemail(), dto.getMbirth(), dto.getMphone(), encryptPassword); // 회원 수정

        log.info("회원 수정 성공");

        log.info("Updated Member: " + member);
    }

    @Override
    public boolean withdrawal(String mid) {
        Member member = memberRepository.findByMid(mid);

        if (member != null) {
            memberRepository.delete(member);
            return true;
        } else {
            throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
        }
    }

    @Override
    public String get_searchId(String mname, String mphone) {
        String result = "";

        try{
            result = memberRepository.searchId(mname, mphone);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 회원가입시 유효성 체크 및 중복 조회 처리
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
