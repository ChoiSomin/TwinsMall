package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.ItemImg;
import com.mall.twins.twinsmall.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeImgRepository extends JpaRepository<NoticeImage, Long> {

    List<NoticeImage> findByNoticeNidOrderByNidAsc(Long NoticeNid);
}
