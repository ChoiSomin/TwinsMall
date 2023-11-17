package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.ShippingDto;

import java.util.List;

public interface ShippingService {

    Long register(ShippingDto ShippingDTO);

    // 조회 (아이디 별로 전체)
    List<ShippingDto> readAll(String mid);

    // 조회 (해당 주소 1개) -> 사용 안 함
    ShippingDto readOne(Long mid);

    // 수정
    void modify(ShippingDto shippingDTO);

    // 삭제
    void remove(Long sno);

    // 기본배송지 수정
    void modifySdefault(String mid);
}