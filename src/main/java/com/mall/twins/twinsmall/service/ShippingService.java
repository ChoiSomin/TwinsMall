package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.ShippingDTO;

import java.util.List;

public interface ShippingService {

    // 등록
    Long register(ShippingDTO ShippingDTO);

    // 조회 (아이디 별로 전체)
    List<ShippingDTO> readAll(String mid);

    // 조회 (해당 주소 1개) -> 사용 안 함
    ShippingDTO readOne(Long mid);

    // 수정
    void modify(ShippingDTO shippingDTO);

    // 삭제
    void remove(Long sno);

    // 기본배송지 수정
    void modifySdefault(String mid);
}
