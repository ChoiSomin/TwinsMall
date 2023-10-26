package com.mall.twins.twinsmall.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ono")
    private Order order;

    @Column(nullable = false, length = 20)
    private int oiprice; // 아이템 단가

    @Column(nullable = false)
    private int oiquantity; // 주문 수량(한번에 5개까지만 구매가능함)

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOiquantity(count);
        orderItem.setOiprice(item.getPprice());

        item.removeStock(count);
        return orderItem;
    } // 주문할 상품의 정보를 담은 객체 생성

    public int getTotalPrice(){
        return oiprice * oiquantity; // 아이템 단가와 주문 수량을 곱한 총액 반환
    }
}






