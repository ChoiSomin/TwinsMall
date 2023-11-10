package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.NoticeSearchDto;
import com.mall.twins.twinsmall.dto.QNoticeFormDto;
import com.mall.twins.twinsmall.entity.QNotice;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public NoticeRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<NoticeFormDto> getNoticeList(NoticeSearchDto noticeSearchDto, Pageable pageable) {
        QNotice notice = QNotice.notice;

        List<NoticeFormDto> content = queryFactory
                .select(
                        new QNoticeFormDto(
                                notice.nid,
                                notice.ntitle,
                                notice.ncontent,
                                notice.regTime,
                                notice.updateTime,
                                notice.view
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
