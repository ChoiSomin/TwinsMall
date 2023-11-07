package com.mall.twins.twinsmall.repository;


import com.mall.twins.twinsmall.constant.ItemSellStatus;
import com.mall.twins.twinsmall.dto.*;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.QItem;
import com.mall.twins.twinsmall.entity.QItemImg;
import com.mall.twins.twinsmall.entity.QNotice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private JPAQueryFactory queryFactory; // 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용

    // JPAQueryFactory의 생성자로 EntityManager 객체를 넣어줌
    public NoticeRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<NoticeFormDto> getNoticeList(Pageable pageable) {

        QNotice notice = QNotice.notice;

        log.info("getMainItemPage query parameters: pageable = {}", pageable);

        List<NoticeFormDto> content = queryFactory
                .select(
                        new QNoticeFormDto(
                                notice.nid,
                                notice.ntitle,
                                notice.ncontent
                                )
                )
                .from(notice)
                .orderBy(notice.nid.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(notice)
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
    }


}
