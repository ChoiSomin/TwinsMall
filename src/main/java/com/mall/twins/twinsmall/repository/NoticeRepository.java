package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long>,
        QuerydslPredicateExecutor<Notice> {

    //공지사항과 멤버를 함께 조회
    @Query("select n, m from Notice n left join n.member m where n.nno = :nno")
    Object getNoticeWithMember(@Param("nno") Long nno);




}
