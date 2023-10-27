package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review> {


    //~~~~~~~~~~~~~~~~~~~~~수정필요
    /*List<Review> findByRcontent(String rcontent);*/

    /*List<Review> findByRcontentOrPdesc(String pname, String pdesc);*/

    @Query("select r from Review r where r.rcontent like %:rcontent%")
    List<Review> findByRcontent(@Param("rcontent") String rcontent);
    //@Param파라미터로 넘어온 값을 JPQL에 들어갈 변수로 지정해 줄 수 있음
    //현재는 itemDetail 변수를 like% ~ % 사이에 :itemDetail 값이 들어가도록 작성

    @Query(value = "select * from review r where r.rcontent like %:rcontent% ", nativeQuery = true)
    List<Review> findByRcontentByNative(@Param("rcontent") String rcontent);

}
