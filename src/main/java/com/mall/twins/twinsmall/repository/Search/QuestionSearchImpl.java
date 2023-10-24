package com.mall.twins.twinsmall.repository.Search;

import com.mall.twins.twinsmall.entity.QQuestion;
import com.mall.twins.twinsmall.entity.Question;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class QuestionSearchImpl extends QuerydslRepositorySupport implements QuestionSearch {

    public QuestionSearchImpl() {
        super(Question.class);
    }

    @Override
    public Page<Question> search1(Pageable pageable) {
        QQuestion question = QQuestion.question; // Q도메인 객체
        JPQLQuery<Question> query = from(question); // select ~ from question
        query.where(question.qtitle.contains("1")); // where qtitle like ~
        this.getQuerydsl().applyPagination(pageable, query);
        List<Question> list = query.fetch();
        long count = query.fetchCount();
        return null;
    }

    @Override
    public Page<Question> searchAll(String[] types, String keyword, Pageable pageable) {

        QQuestion question = QQuestion.question;
        JPQLQuery<Question> query = from(question);

        if((types != null && types.length > 0) && keyword != null) { // 검색 조건과 키워드가 있다면
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types) {
                switch(type) {
                    case "t":
                        booleanBuilder.or(question.qtitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(question.qcontent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(question.createdBy.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(question.qno.gt(2L));
        this.getQuerydsl().applyPagination(pageable, query);
        List<Question> list = query.fetch();
        long count = query.fetchCount();
        return null;
    }
}
