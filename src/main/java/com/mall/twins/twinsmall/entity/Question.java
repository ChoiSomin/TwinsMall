package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="question")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qno; //질문 번호(pk)

    @Column(nullable = false)
    private String qtitle; //질문제목

    @Column(nullable = false, length = 5000)
    private String qcontent; //질문내용

    @Column(nullable = false)
    private String qcate; //카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자


}
