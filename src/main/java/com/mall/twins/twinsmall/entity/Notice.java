package com.mall.twins.twinsmall.entity;

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
    private Long nno;

    @Column(nullable = false)
    private String ntitle;

    @Lob
    @Column(nullable = false, length = 5000)
    private String ncontent;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    /*@ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자*/

    public void updateNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public void updateNcontent(String ncontent) {
        this.ncontent = ncontent;
    }
}
