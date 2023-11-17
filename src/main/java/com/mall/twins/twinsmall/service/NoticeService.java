package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.entity.Notice;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {

    Long register(NoticeFormDto dto); // 공지 작성

    PageResultDTO<NoticeFormDto, Notice> getNoticeList(PageRequestDTO pageRequestDTO);

    NoticeFormDto read(Long nno);

    void modify(NoticeFormDto noticeFormDto);

    void remove(Long nno);

    /* 추가 LJM */
    Page<Notice> getList(int page);

    default Notice dtoToEntity(NoticeFormDto dto) {

        Notice notice = Notice.builder()
                .nno(dto.getNno())
                .ntitle(dto.getNtitle())
                .ncontent(dto.getNcontent())
                .view(dto.getView())
                .build();
        return notice;
    }

    default NoticeFormDto entityToDTO(Notice notice) {

        NoticeFormDto noticeFormDto = NoticeFormDto.builder()
                .nno(notice.getNno())
                .ntitle(notice.getNtitle())
                .ncontent(notice.getNcontent())
                .regTime(notice.getRegTime())
                .updateTime(notice.getUpdateTime())
                .view(notice.getView())
                .build();

        return noticeFormDto;

    }

    /*Long saveNotice(NoticeFormDto noticeFormDto);*/
}
