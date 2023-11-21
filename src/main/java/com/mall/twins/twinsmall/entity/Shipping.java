package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="shipping")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class Shipping{

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


    private String saddress; // 상세주소

    @Column(nullable = false)
    private String sphone; // 휴대폰 번호


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private Member member;

    // 배송지명 수정
    public void change(String sname, String sperson, String zonecode, String address, String saddress, String sphone ){
        this.sname = sname;
        this.sperson = sperson;
        this.zonecode = zonecode;
        this.address = address;
        this.saddress = saddress;
        this.sphone = sphone;
    }

    public static Shipping createShipping(Member member){
        Shipping shipping = new Shipping();
        shipping.setMember(member);
        return shipping;
    }

}
