package com.mall.twins.twinsmall.entity;

import com.mall.twins.twinsmall.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    private Member member;

    private LocalDateTime odate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus ostatus; //주문상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL 
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();
    // mappedBy = "order" -> 양방향 관계에서 주인 엔티티는 order
    // cascade = CascadeType.ALL -> order 엔티티에 대한 변경이 orderItem 엔티티에도 적용됨
    // orphanRemoval = true -> order 엔티티 (부모)에서 제거된 orderItem 엔티티 (자식)은 영속성 컨텍스트에서 자동으로 삭제됨
    // fetch = FetchType.LAZY -> 지연 로딩을 사용하여 orderItem 엔티티를 실제로 사용할 때에만 로딩되도록 설정

    /*public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem); // 주문 상품 정보
        orderItem.setOrder(this); // 양방향 매핑이기 때문에 orderItem에도 order 객체를 넣어줘야 한다
    } // 주문 상품 객체들을 이용해서 주문 객체를 만듦

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member); // 상품을 주문한 회원의 정보

        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
            // 장바구니에는 여러 상품이 담길 수 있어서 리스트 형태로 값을 받음
        }

        order.setOrderStatus(OrderStatus.ORDER); // 주문 상태 변경
        order.setOrderDate(LocalDateTime.now()); // 현재 시간을 주문 시간으로 변경
        return order;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    } // 주문 총액

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
    } // 주문 상태를 취소 상태로 변경*/

}