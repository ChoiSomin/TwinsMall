package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.dto.ShippingDto;

import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.entity.Shipping;
import com.mall.twins.twinsmall.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShippingService {

    Long register(ShippingDto ShippingDTO);

    // 조회 (아이디 별로 전체)
    List<ShippingDto> readAll(String mid);

    PageResultDTO<ShippingDto, Shipping> getList(PageRequestDTO requestDTO);


    // 조회 (해당 주소 1개)
    ShippingDto readOne(Long sno);

    // 수정
    void modify(ShippingDto shippingDTO);

    // 삭제
    void remove(Long sno);

    // 기본배송지 수정
    /*void modifySdefault(String mid);*/
}