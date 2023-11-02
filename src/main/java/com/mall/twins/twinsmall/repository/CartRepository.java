package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberMno(Long mno);
}
