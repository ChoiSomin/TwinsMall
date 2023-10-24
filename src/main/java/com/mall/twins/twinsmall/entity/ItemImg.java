package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="itemimage")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "item")
public class ItemImg extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iimgno;

    private String iimgnew; //이미지 파일명

    private String iimgori; //원본 이미지 파일명

    private String iimgurl; //이미지 조회 경로

    private String iimgrep; //대표 이미지 여부


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Item item;

    public void updateItemImg(String iimgori, String iimgnew, String iimgurl){
        // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력받아 이미지 정보를 업데이트
        this.iimgori = iimgori;
        this.iimgnew = iimgnew;
        this.iimgurl = iimgurl;
    }

}