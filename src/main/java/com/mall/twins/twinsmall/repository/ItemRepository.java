package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findById(String pname);
}
