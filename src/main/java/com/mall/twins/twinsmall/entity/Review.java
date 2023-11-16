package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Table(name="review")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString(exclude = {"item" ,"member", "orders"})
public class Review extends BaseEntity {

    @Id
    @Column(name="rno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    @Lob
    @Column(nullable = false, length = 1000)
    private String rcontent;    // 리뷰내용

    @Column(nullable = false)
    private int rscore;      // 평점

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;



}