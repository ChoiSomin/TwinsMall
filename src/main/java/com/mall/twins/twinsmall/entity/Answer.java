package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="answer")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Answer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano; //답변 번호(pk)

    @Column(nullable = false, length = 5000)
    private String acontent; //답변내용

    @OneToOne(fetch = FetchType.LAZY)
    private Member member; //작성자(작성자 한 사람이 답변 1개만 달 수 있음)

    @OneToOne(fetch = FetchType.LAZY)
    private Question question; //질문(질문 하나당 답변 1개)
}
