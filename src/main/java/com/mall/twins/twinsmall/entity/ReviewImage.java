package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="reviewimage")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "review")
public class ReviewImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rimgno;

    private String rimgnew; //이미지 파일명

    private String rimgori; //원본 이미지 파일명

    private String rimgurl; //이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rno")
    private Review review;

    public void updateItemImg(String rimgori, String rimgnew, String rimgurl){
        // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력받아 이미지 정보를 업데이트
        this.rimgori = rimgori;
        this.rimgnew = rimgnew;
        this.rimgurl = rimgurl;
    }

}
