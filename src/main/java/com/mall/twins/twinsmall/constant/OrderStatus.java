package com.mall.twins.twinsmall.constant;

import lombok.Getter;

public enum OrderStatus {

    ORDER("결제 완료"), SHIPPING("배송 중"), CANCEL("취소"), RETURN("환불"), COMPLETE("배송 완료");

    @Getter
    public String korean;

    OrderStatus(String kor) {
        this.korean = kor;
    }
}
