package com.mall.twins.twinsmall.repository;


import com.mall.twins.twinsmall.dto.ItemSearchDto;
import com.mall.twins.twinsmall.dto.MainItemDto;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice> {

    List<Notice> findByNtitle(String ntitle);

    List<Notice> findByNtitleOrNcontent(String ntitle, String ncontent);

    @Query("select n from Notice n where n.ncontent like %:ncontent% ")
    List<Notice> findByNcontent(@Param("ncontent") String ncontent);
    //@Param파라미터로 넘어온 값을 JPQL에 들어갈 변수로 지정해 줄 수 있음
    //현재는 itemDetail 변수를 like% ~ % 사이에 :itemDetail 값이 들어가도록 작성

    @Query(value = "select * from notice n where n.ncontent like %:ncontent%", nativeQuery = true)
    List<Notice> findByNcontentByNative(@Param("ncontent") String ncontent);

    @Modifying
    @Query(value = "update Notice n set n.view = n.view + 1 where n.nid = :nid")
    int updateView(@Param("nid") Long id);




}
