package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.constant.MemberRole;
import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.QMember;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.security.dto.MemberSecurityDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public PageResultDTO<MemberJoinDTO, Member> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("mid").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색조건처리

        Page<Member> result = memberRepository.findAll(booleanBuilder, pageable);
        //Querydsl 사용

        Function<Member, MemberJoinDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QMember qMember = QMember.member;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qMember.mid.gt("0L"); //nno>0 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            //검색조건이 없을때
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qMember.mname.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qMember.mid.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qMember.memail.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }

    @Override
    public MemberSecurityDTO readOne(String mid) {

        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.orElseThrow(() -> new RuntimeException("mid에 해당하는 회원이 없습니다: " + mid));

        List<GrantedAuthority> authorities = Optional.ofNullable(member.getRoleSet())
                .orElse(Collections.emptySet())
                .stream()
                .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                .collect(Collectors.toList());

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getMid(),
                member.getMpw(),
                member.getMname(),
                member.getMemail(),
                member.getMbirth(),
                member.getMphone(),
                member.isMdel(),
                member.isMsocial(),
                authorities  // 이곳에서 권한 목록을 사용합니다
        );

        return memberSecurityDTO;
    }

    /*@Override
    public MemberSecurityDTO modify(MemberSecurityDTO dto) {

        Optional<Member> result = memberRepository.findById(dto.getMid());

        if (result.isPresent()) {

            Member entity = result.get();

            entity.changePassword(dto.getMpassword());
            entity.changeName(dto.getMname());
            entity.changeEmail(dto.getMemail());
            entity.changeAddress(dto.getMaddress());
            entity.changePhonenum(dto.getMphonenum());
            entity.isMdel();
            entity.isMsocial();



            memberRepository.save(entity);
        }

        return dto;
    }*/


    @Override
    public MemberSecurityDTO modify(MemberSecurityDTO dto) {

        Optional<Member> result = memberRepository.findById(dto.getMid());

        if (result.isPresent()) {

            Member entity = result.get();

            // Password가 null이 아닌 경우에만 변경
            if (dto.getMpw() != null) {
                entity.changePassword(passwordEncoder.encode(dto.getMpw()));
            }

            entity.changeName(dto.getMname());
            entity.changeEmail(dto.getMemail());
            entity.changePhone(dto.getMphone());
            entity.changeDel(dto.getMdel() != null ? dto.getMdel() : false);  // 수정 부분
            entity.changeSocial(dto.getMsocial() != null ? dto.getMsocial() : false);  // 수정 부분



            memberRepository.save(entity);
        }

        return dto;
    }

    @Override
    public void remove(String mid) {

        memberRepository.deleteById(mid);

    }

}





