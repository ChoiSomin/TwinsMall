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
    @Column(nullable = false, length = 50)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;
    
    private String sname; // 배송지명

    private String sperson; // 받는 분

    private String zonecode; // 우편번호

    private String address; // 주소
    
    private String saddress; // 상세주소
    
    private String sphone; // 휴대폰 번호
    
    private String sdefault; // 기본 배송지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    private Member member;

}
