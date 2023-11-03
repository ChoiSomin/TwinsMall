package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    // Optional<Item> findByPname(@Param("pname") String pname); // Review에서 사용

    List<Item> findByPname(String pname);

    List<Item> findByPnameOrPdesc(String pname, String pdesc);

    List<Item> findByPpriceLessThan(Integer pprice);

    List<Item> findByPpriceLessThanOrderByPpriceDesc(Integer pprice);

    @Query("select i from Item i where i.pdesc like %:pdesc% order by i.pprice desc")
    List<Item> findByPdesc(@Param("pdesc") String pdesc);
    //@Param파라미터로 넘어온 값을 JPQL에 들어갈 변수로 지정해 줄 수 있음
    //현재는 itemDetail 변수를 like% ~ % 사이에 :itemDetail 값이 들어가도록 작성

    @Query(value = "select * from item i where i.pdesc like %:pdesc% order by i.pprice desc", nativeQuery = true)
    List<Item> findByPdescByNative(@Param("pdesc") String pdesc);

}
