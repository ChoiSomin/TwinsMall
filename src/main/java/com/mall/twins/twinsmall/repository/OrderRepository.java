package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
