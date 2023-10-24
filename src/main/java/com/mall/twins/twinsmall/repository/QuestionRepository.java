package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Question;
import com.mall.twins.twinsmall.repository.Search.QuestionSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionSearch {

    @Query(value = "select now()", nativeQuery = true)
    String getTime();

}
