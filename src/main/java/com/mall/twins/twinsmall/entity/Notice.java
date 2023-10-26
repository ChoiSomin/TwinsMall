package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="notice")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nno;

    @Column(nullable = false)
    private String ntitle;

    @Column(nullable = false, length = 5000)
    private String ncontent;

    @ManyToOne(fetch = FetchType.LAZY)
    /*@JoinColumn(name = "member_mid")*/
    private Member member; //작성자



    //수정용 메서드
    //제목 수정
    public void changeTitle(String ntitle){
        this.ntitle = ntitle;
    }


    //내용 수정
    public void changeContent(String ncontent){
        this.ncontent = ncontent;
    }


}
