package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mno")
    private Member member;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    } // 회원 하나당 하나의 장바구니를 가진다

}