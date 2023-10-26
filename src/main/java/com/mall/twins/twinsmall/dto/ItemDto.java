package com.mall.twins.twinsmall.dto;


import com.mall.twins.twinsmall.constant.ItemSellStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ItemDto {

    private Long pno;

    private String pname; //상품명

    private int pprice;    //가격

    private Integer pstock;      // 재고

    private String pcate;  // 카테고리

    private String pdesc; // 상품 상세 설명

    private LocalDate relaseyear; // 발매일

    private ItemSellStatus pstatus; //상품 판매 상태
}
