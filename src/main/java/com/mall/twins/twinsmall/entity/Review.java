package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Table(name="review")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"item","member", "order"})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @Column(nullable = false, length = 500)
    private String rcontent;    // 리뷰 내용

    @Column(nullable = false, length = 5)
    private int rscore;      // 평점

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

}
