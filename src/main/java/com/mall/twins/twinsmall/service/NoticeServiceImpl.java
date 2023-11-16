package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.*;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.entity.QNotice;
import com.mall.twins.twinsmall.repository.NoticeRepository;
import com.mall.twins.twinsmall.repository.NoticeRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.dynamic.ClassFileLocator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final EntityManager entityManager;

    @Override
    public Long register(NoticeFormDto dto) {

        Notice notice = Notice.builder()
                .nno(dto.getNno())
                .ntitle(dto.getNtitle())
                .ncontent(dto.getNcontent())
                .view(dto.getView())
                .build();

        noticeRepository.save(notice);

        return notice.getNno();
    }
    @Transactional(readOnly = true)
    public PageResultDTO<NoticeFormDto, Notice> getNoticeList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("nno").descending());

        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);

        Page<Notice> result = noticeRepository.findAll(booleanBuilder, pageable);

        log.info(result);

        Function<Notice, NoticeFormDto> fn = (notice -> entityToDTO(notice));

        log.info(fn);

        return new PageResultDTO<>(result, fn);
    }

    @Transactional(readOnly = true)
    @Override
    public NoticeFormDto read(Long nno) {

        Notice notice = noticeRepository.findById(nno)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID: " + nno));

        // 여기에서 view 값을 확인
        log.info("View value before increment: " + notice.getView());

        // 조회수 증가
        notice.addView();

        // 여기에서 view 값을 다시 확인
        log.info("View value after increment: " + notice.getView());

        // 저장
        noticeRepository.save(notice);

        entityManager.flush(); // 수동으로 flush 호출

        return NoticeFormDto.of(notice);
    }

    @Transactional
    @Override
    public void modify(NoticeFormDto noticeFormDto) {

        Optional<Notice> result = noticeRepository.findById(noticeFormDto.getNno());

        if(result.isPresent()){

            Notice notice = result.get();

            notice.updateNtitle(noticeFormDto.getNtitle());
            notice.updateNcontent(noticeFormDto.getNcontent());

            noticeRepository.save(notice);
        }

    } // modify 구현

    @Transactional
    public void remove(Long nno) {
        noticeRepository.deleteById(nno);
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO){ // Querydsl 처리

        String type = pageRequestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QNotice qNotice = QNotice.notice;

        String keyword = pageRequestDTO.getKeyword();

        // 검색 조건이 없다면 nno > 0 으로만 생성된다
        BooleanExpression expression = qNotice.nno.gt(0L); // nno > 0 조건만 생성

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0 ){ // 검색 조건이 없는경우
            return booleanBuilder;
        }

        // 검색 조건 작성
        // PageRequestDTO 를 파라미터로 받아 검색조건이 있는 경우 conditionBuilder 변수를 생성해 각 검색조건을 or로 연결해 처리한다
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qNotice.ntitle.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qNotice.ncontent.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }


}
