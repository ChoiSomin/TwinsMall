package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResponseDTO;
import com.mall.twins.twinsmall.dto.QuestionDTO;
import com.mall.twins.twinsmall.entity.Question;
import com.mall.twins.twinsmall.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;

    @Override
    public Long register(QuestionDTO questionDTO) {
        Question question = modelMapper.map(questionDTO, Question.class);
        Long qno = questionRepository.save(question).getQno();
        return qno;
    }

    @Override
    public QuestionDTO readOne(Long qno) {
        Optional<Question> result = questionRepository.findById(qno);
        Question question = result.orElseThrow();
        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
        return questionDTO;
    }

    @Override
    public PageResponseDTO<QuestionDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("qno");

        Page<Question> result = questionRepository.searchAll(types, keyword, pageable);

        List<QuestionDTO> questionDTOList = result.getContent().stream()
                                                                .map(question -> modelMapper.map(question, QuestionDTO.class))
                                                                .collect(Collectors.toList());

        return PageResponseDTO.<QuestionDTO>withAll()
                                .pageRequestDTO(pageRequestDTO)
                                .dtoList(questionDTOList)
                                .total((int)result.getTotalElements())
                                .build();
    }

}
