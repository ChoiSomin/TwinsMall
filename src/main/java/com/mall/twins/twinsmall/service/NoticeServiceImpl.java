package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.NoticeDTO;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.entity.QNotice;
import com.mall.twins.twinsmall.repository.NoticeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{


    private final NoticeRepository repository;

    @Override
    public Long register(NoticeDTO dto) {//공지사항 등록

        log.info("notice register메서드 실행중 + DTO");
        log.info(dto);

        Notice entity = dtoToEntity(dto);

        log.info(entity);
        repository.save(entity);

        return entity.getNno();
    }

    @Override
    public NoticeDTO read(Long nno) {//공지사항 조회(상세)

        Optional<Notice> result = repository.findById(nno);


        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void remove(Long nno) {//공지사항 삭제

        repository.deleteById(nno);

    }

    @Override
    public void modify(NoticeDTO dto) {//공지사항 수정

        Optional<Notice> result = repository.findById(dto.getNno());

        if (result.isPresent()) {

            Notice entity = result.get();

            entity.changeTitle(dto.getNtitle());
            entity.changeContent(dto.getNcontent());

            repository.save(entity);
        }
    }

    @Override
    public PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO) {//공지사항 조회(목록)

            Pageable pageable = requestDTO.getPageable(Sort.by("nno").descending());

            BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색조건처리

            Page<Notice> result = repository.findAll(booleanBuilder, pageable);
            //Querydsl 사용

            Function<Notice, NoticeDTO> fn = (entity -> entityToDto(entity));

            return new PageResultDTO<>(result, fn);
    }



    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type=requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QNotice qNotice = QNotice.notice;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qNotice.nno.gt(0L); //nno>0 조건만 생성

        booleanBuilder.and(expression);

        if(type==null || type.trim().length() == 0) {
            //검색조건이 없을때
            return booleanBuilder;
        }

        //검색조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){//제목
            conditionBuilder.or(qNotice.ntitle.contains(keyword));
        }
        if(type.contains("c")){//내용
            conditionBuilder.or(qNotice.ncontent.contains(keyword));
        }
        if(type.contains("w")){//작성자(DB연관관계 때문에 한번에 mid를 가져올 수 없음)
            conditionBuilder.or(qNotice.member.mid.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }

}
