package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.ItemSearchDto;
import com.mall.twins.twinsmall.dto.MainItemDto;
import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.NoticeSearchDto;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.repository.NoticeRepository;
import com.mall.twins.twinsmall.repository.NoticeRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    private final NoticeRepositoryCustom noticeRepositoryCustom;

    public Long saveNotice(NoticeFormDto noticeFormDto) throws Exception{

        // 등록
        Notice notice = noticeFormDto.createNotice();
        System.out.println("Notice Detail: " + notice.getNcontent()); // 로그 출력
        System.out.println("Notice Detail: " + notice.getNtitle());
        noticeRepository.save(notice);

        return notice.getNid();

    }

    @Transactional(readOnly = true)
    public NoticeFormDto getNoticeDtl(Long NoticeNid){

        Notice notice = noticeRepository.findById(NoticeNid).orElseThrow(EntityNotFoundException::new); // 상품의 아이디를 통해 상품 엔티티를 조회

        NoticeFormDto noticeFormDto = NoticeFormDto.of(notice);

        return noticeFormDto;
    }


    public Long updateNotice(NoticeFormDto noticeFormDto) throws Exception{
        //상품 수정
        Notice notice = noticeRepository.findById(noticeFormDto.getNno())
                .orElseThrow(EntityNotFoundException::new);
        notice.updateNotice(noticeFormDto);

        return notice.getNid();
    }

    @Transactional(readOnly = true)
    public Page<NoticeFormDto> getNoticeList(NoticeSearchDto noticeSearchDto, Pageable pageable){

        return noticeRepositoryCustom.getNoticeList(noticeSearchDto, pageable);
    }
}
