package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.ItemSellStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {

    private Long id;

    private String pname;

    private String pdesc;

    private String iimgurl;

    private Integer pprice;

    private String pcate;

    private Integer pstock;

    private ItemSellStatus pstatus;

    @QueryProjection // querydsl로 결과 조회 시 MainItemDto 객체로 바로 받아옴
    public MainItemDto(Long id, String pname, String pdesc, String iimgurl, Integer pprice, String pcate, Integer pstock, ItemSellStatus pstatus){
        this.id = id;
        this.pdesc = pdesc;
        this.pname = pname;
        this.iimgurl = iimgurl;
        this.pprice = pprice;
        this.pcate = pcate;
        this.pstock = pstock;
        this.pstatus = pstatus;
    }
}
