package com.mall.twins.twinsmall.repository;


import com.mall.twins.twinsmall.dto.CartDetailDto;
import com.mall.twins.twinsmall.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartCidAndItemId(Long cid, Long id);

    @Query("select  new com.mall.twins.twinsmall.dto.CartDetailDto(ci.cino, i.pname, i.pprice, ci.count, im.iimgurl)" +
            "from CartItem ci, ItemImg  im join ci.item i where ci.cart.cid = :cid and im.item.id = ci.item.id and im.iimgrep = 'Y' order by ci.regTime desc ")
    List<CartDetailDto> findCartDetailDtoList(long cid);

}
