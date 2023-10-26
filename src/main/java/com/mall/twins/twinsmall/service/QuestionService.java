package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResponseDTO;
import com.mall.twins.twinsmall.dto.QuestionDTO;

public interface QuestionService {

    Long register(QuestionDTO questionDTO); // 질문글 등록
    QuestionDTO readOne(Long qno); // 질문글 상세보기
    PageResponseDTO<QuestionDTO> list(PageRequestDTO pageRequestDTO); // 질문글 목록

}
