package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdAsc(Long id);

    //ItemImg findByPnoAndIimgrep(Long id, String iimgrep);

    /*ItemImg findByPnoAndIimgrep(Long pno, String iimgrep); // 상품의 대표 이미지를 찾음*/
}
