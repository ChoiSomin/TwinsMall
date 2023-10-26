package com.mall.twins.twinsmall.repository.search;


import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.entity.QMember;
import com.mall.twins.twinsmall.entity.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchNoticeRepositoryImpl extends QuerydslRepositorySupport implements SearchNoticeRepository{


    public SearchNoticeRepositoryImpl(){
        super(Notice.class);
    }

    @Override
    public Notice search1() {

        log.info("search1 실행중");

        QNotice notice = QNotice.notice;
        QMember member = QMember.member;

        JPQLQuery<Notice> jpqlQuery = from(notice);
        jpqlQuery.leftJoin(member).on(notice.member.eq(member));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(notice, member.mid);
        tuple.groupBy(notice);

        log.info("===================================================================");
        log.info(tuple);
        log.info("===================================================================");

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage 실행중!!!!!!!!!!!!!!!!");

        QNotice notice = QNotice.notice;
        QMember member = QMember.member;

        JPQLQuery<Notice> jpqlQuery = from(notice);
        jpqlQuery.leftJoin(member).on(notice.member.eq(member));

        //select c, m count(a) from Cscenter c left join c.mid m left join Answer a on a.service = c
        JPQLQuery<Tuple> tuple = jpqlQuery.select(notice, member);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = notice.nno.gt(0L); //pk의 인덱싱 기능 사용

        booleanBuilder.and(expression);

        if (type != null){
            String[] typeArr = type.split("");

            //검색 조건 작성
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t:typeArr){
                switch (t){
                    case "t": //제목
                        conditionBuilder.or(notice.ntitle.contains(keyword));
                        break;
                    case "c": //내용
                        conditionBuilder.or(notice.ncontent.contains(keyword));
                        break;
                    case "w": //작성자
                        conditionBuilder.or(member.mid.contains(keyword));
                        break;

                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);

        //order by
        Sort sort = pageable.getSort();

        //tuple.orderBy(cscenter.sno.desc());

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Notice.class, "notice");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        tuple.groupBy(notice);

        //page처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();
        log.info("COUNT:"+count);

        return new PageImpl<Object[]>
                (result.stream().map(t -> t.toArray()).collect(Collectors.toList()),pageable,count);
    }
}
