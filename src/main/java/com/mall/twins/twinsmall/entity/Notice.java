package com.mall.twins.twinsmall.entity;

import com.mall.twins.twinsmall.dto.ItemFormDto;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="notice")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice extends BaseEntity{

    @Id
    @Column(name="nno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nid;

    @Column(nullable = false)
    private String ntitle;

    @Lob
    @Column(nullable = false, length = 5000)
    private String ncontent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자

    public void updateNotice(NoticeFormDto noticeFormDto){
        this.ntitle = noticeFormDto.getNtitle();
        this.ncontent = noticeFormDto.getNcontent();
    }


}
