package com.mall.twins.twinsmall.entity;

import javax.persistence.*;

@Entity
public class MemberImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mimgno; //이미지 번호

    private String mimgori; //원본 파일명

    private String mimgnew; //저장 파일명

    private String mimgUrl; //이미지 조회 경로


    //private Member member; //



}
