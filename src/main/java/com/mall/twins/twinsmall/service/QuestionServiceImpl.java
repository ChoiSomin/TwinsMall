package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.QuestionDTO;
import com.mall.twins.twinsmall.entity.Question;
import com.mall.twins.twinsmall.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
