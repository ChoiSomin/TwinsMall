package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeSearchDto {

    private String searchDateType; // 현재 시간과 공지 등록일을 비교해서 공지 데이터를 조회

    private String searchBy; // 공지를 조회할 때 어떤 유형으로 조회할 지 선택 (제목, 내용)

    private String searchQuery = ""; // 조회할 검색어 저장할 변수

}
