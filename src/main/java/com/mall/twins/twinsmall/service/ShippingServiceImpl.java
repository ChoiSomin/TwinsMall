package com.mall.twins.twinsmall.service;


import com.mall.twins.twinsmall.config.auth.MyUserDetailsService;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.dto.ShippingDto;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.entity.QShipping;
import com.mall.twins.twinsmall.entity.Shipping;
import com.mall.twins.twinsmall.repository.MemberRepository;
import com.mall.twins.twinsmall.repository.ShippingRepository;
import com.mall.twins.twinsmall.security.CustomOAuth2UserService;
import com.mall.twins.twinsmall.security.CustomUserDetailsService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShippingServiceImpl implements ShippingService{

    private final ModelMapper modelMapper;                  // 엔티티(Board)와 DTO(BoardDTO) 간의 변환
    private final ShippingRepository shippingRepository;    // 엔티티 객체 연결
    private final MemberRepository memberRepository;


    // 등록
    @Override
    public Long register(ShippingDto shippingDto) {

        Shipping shipping = modelMapper.map(shippingDto, Shipping.class);
        Member member = memberRepository.findByMid(shippingDto.getMid());
        shipping.setMember(member);
        return shippingRepository.save(shipping).getSno();
    }

    // 기본 배송지 업데이트
    @Override
    public void updateDefaultShipping(String mid) {
        // 현재 기본 배송지를 해제하는 로직 추가
        shippingRepository.updateDefaultShipping(mid);
    }


    // 조회 -> mid(사용자 아이디)로 배송 주소 DTO 전체 리스트 리턴
    @Override
    public List<ShippingDto> readAll(String mid) {

        List<Shipping> shippingList = shippingRepository.listOfShipping(mid);

        List<ShippingDto> shippingDTOList = shippingList.stream()
                .map(data -> modelMapper.map(data, ShippingDto.class))
                .collect(Collectors.toList());

        return shippingDTOList;
    }

    @Override
    public PageResultDTO<ShippingDto, Shipping> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("sno").descending());

        BooleanBuilder booleanBuilder = getSerch(requestDTO);

        Page<Shipping> result = shippingRepository.findAll(booleanBuilder, pageable);

        Function<Shipping, ShippingDto> fn = (entity -> ShippingDto.of((entity)));

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSerch(PageRequestDTO requestDTO) {

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QShipping qShipping = QShipping.shipping;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qShipping.sno.gt(0L);

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        /*if (type.contains("t")){
            conditionBuilder.or(qShipping.title.contains(keyword));
        }
        if (type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }*/

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }


    @Override
    public ShippingDto readOne(Long sno) {

        Optional<Shipping> result = shippingRepository.findBySno(sno);
        Shipping shipping = result.orElseThrow();
        ShippingDto shippingDTO = modelMapper.map(shipping, ShippingDto.class);

        return shippingDTO;
    }

    @Override
    public void modify(ShippingDto shippingDTO) {

        Optional<Shipping> result = shippingRepository.findById(shippingDTO.getSno());
        Shipping shipping = result.orElseThrow();
        shipping.change(shippingDTO.getSname(), shippingDTO.getSperson(), shippingDTO.getZonecode(), shippingDTO.getAddress(), shippingDTO.getSaddress(), shippingDTO.getSphone());
        shippingRepository.save(shipping);

    }

    @Override
    public void remove(Long sno) {

        shippingRepository.deleteById(sno);

    }

   /* @Override
    public void modifySdefault(String mid) {

        shippingRepository.updateSdefault(mid);

    }*/
}