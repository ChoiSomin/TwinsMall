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
    private String nno;

    @Column(nullable = false)
    private String ntitle;

    @Column(nullable = false, length = 5000)
    private String ncontent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자


}
