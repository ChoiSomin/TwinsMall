package com.mall.twins.twinsmall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cartitem")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cino")
    private Long cino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cid")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Item item;

    public static CartItem createCartItem(Cart cart, Item item) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        return cartItem;
    } // 장바구니에 담을 상품 엔티티 생성

}