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
    @Column(nullable = false, length = 255)
    private String nno;

    @Column(nullable = false, length = 50)
    private String ntitle;

    @Column(nullable = false, length = 25)
    private String ncontent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자


}
