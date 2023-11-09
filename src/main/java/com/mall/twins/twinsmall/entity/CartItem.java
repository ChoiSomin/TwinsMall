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
    private Long cino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cid")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Item item;

    @Column
    private int count;

    public static CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);

        return cartItem;
    } // 장바구니에 담을 상품 엔티티 생성

    public void addCount(int count){
        this.count += count;
    }

    public void updateCount(int count){ this.count = count; }

}