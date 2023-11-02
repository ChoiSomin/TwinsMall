package com.mall.twins.twinsmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDTO<E> {

    private Long sno;

    private String mid;         // 회원 DB와 연결

    @NotEmpty
    private String sname;       // 배송지명

    @NotEmpty
    private String sperson;     // 받는 분

    @NotEmpty
    private String zonecode;    // 우편번호

    @NotEmpty
    private String address;     // 주소

    @NotEmpty
    private String saddress;    // 상세 주소

    @NotEmpty
    private String sphone;   // 휴대폰 번호

    private String sdefault;   // 기본배송지

    private List<E> dtoList;
}
