package com.mall.twins.twinsmall.entity;

import com.mall.twins.twinsmall.constant.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="shipping")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class Shipping extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    @Column(nullable = false)
    private String sname; // 배송지명

    @Column(nullable = false)
    private String sperson; // 받는 분

    @Column(nullable = false)
    private String zonecode; // 우편번호

    @Column(nullable = false, length = 500)
    private String address; // 주소

    @Column(nullable = false)
    private String saddress; // 상세주소

    @Column(nullable = false)
    private String sphone; // 휴대폰 번호

    @Column(nullable = false)
    private String sdefault; // 기본 배송지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    private Member member;

}
