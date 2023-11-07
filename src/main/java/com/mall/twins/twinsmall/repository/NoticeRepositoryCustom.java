package com.mall.twins.twinsmall.repository;


import com.mall.twins.twinsmall.dto.ItemSearchDto;
import com.mall.twins.twinsmall.dto.MainItemDto;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {

    Page<NoticeFormDto> getNoticeList(Pageable pageable);

}