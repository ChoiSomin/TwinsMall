package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.ItemImg;
import com.mall.twins.twinsmall.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findByReviewRidOrderByRidAsc(Long ReviewRid);
}
