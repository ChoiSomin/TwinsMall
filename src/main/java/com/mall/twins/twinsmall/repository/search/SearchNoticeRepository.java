package com.mall.twins.twinsmall.repository.search;

import com.mall.twins.twinsmall.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchNoticeRepository {

    Notice search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
