package com.mall.twins.twinsmall.repository.Search;

import com.mall.twins.twinsmall.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionSearch {

    Page<Question> search1(Pageable pageable);

    Page<Question> searchAll(String[] types, String keyword, Pageable pageable);

}
