package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="noticeimage")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "notice")
public class NoticeImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nimgno;

    private String nimgnew; //이미지 파일명

    private String nimgori; //원본 이미지 파일명

    private String nimgurl; //이미지 조회 경로
    
    private String nimgrep; // 대표 이미지 여부


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nno")
    private Notice notice;

    public void updateItemImg(String nimgori, String nimgnew, String nimgurl){
        // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력받아 이미지 정보를 업데이트
        this.nimgori = nimgori;
        this.nimgnew = nimgnew;
        this.nimgurl = nimgurl;
    }
}
