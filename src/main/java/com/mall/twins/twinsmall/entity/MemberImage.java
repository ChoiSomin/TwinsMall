package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="memberimage")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class MemberImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mimgno;

    private String mimgnew; //이미지 파일명

    private String mimgori; //원본 이미지 파일명

    private String mimgurl; //이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    private Member member;

    public void updateItemImg(String mimgori, String mimgnew, String mimgurl){
        // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력받아 이미지 정보를 업데이트
        this.mimgori = mimgori;
        this.mimgnew = mimgnew;
        this.mimgurl = mimgurl;
    }

}