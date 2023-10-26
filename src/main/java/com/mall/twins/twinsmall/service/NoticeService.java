package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.NoticeDTO;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.entity.Member;
import com.mall.twins.twinsmall.entity.Notice;

public interface NoticeService {

        Long register(NoticeDTO dto);

        NoticeDTO read(Long nno);

        void remove(Long nno);

        void modify(NoticeDTO dto);

        default Notice dtoToEntity(NoticeDTO dto) {

            Member member = Member.builder()
                    .mid(dto.getMember())
                    .build();

            Notice entity = Notice.builder()
                    .nno(dto.getNno())
                    .ntitle(dto.getNtitle())
                    .ncontent(dto.getNcontent())
                    .member(member)
                    .build();

            return entity;
        }

        default NoticeDTO entityToDto(Notice entity) {

            NoticeDTO dto = NoticeDTO.builder()
                    .nno(entity.getNno())
                    .ntitle(entity.getNtitle())
                    .ncontent(entity.getNcontent())
                    .member(entity.getMember().getMid())
                    .regTime(entity.getRegTime())
                    .updateTime(entity.getUpdateTime())
                    .build();

            return dto;
        }

        PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO);

}
