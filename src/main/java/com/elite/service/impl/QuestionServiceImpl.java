package com.elite.service.impl;

import au.com.bytecode.opencsv.CSVReader;
import com.elite.domain.Question;
import com.elite.domain.QuestionOption;
import com.elite.dto.QuestionDTO;
import com.elite.dto.QuestionOptionDTO;
import com.elite.repository.QuestionRepository;
import com.elite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by binaychap on 2/26/2016.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void importQuestionCSV(InputStream questions) throws IOException {
        QuestionDTO questionDTOs = readQuestionDTOs(questions);
        if (questionDTOs != null) {
            save(questionDTOs);
        }
    }


    private QuestionDTO readQuestionDTOs(final InputStream input) throws IOException {
        QuestionDTO questionDTO = new QuestionDTO();
        final List<QuestionOptionDTO> optionDTOs = new ArrayList<>();

        final CSVReader reader = openCSV(input);
        String[] line;
        while ((line = reader.readNext()) != null) {
            String questionName = line[0];
            questionDTO.setName(questionName);
            for (int i = 1; i < line.length - 1; i++) {
                String name = line[i];
                optionDTOs.add(new QuestionOptionDTO(name));
            }

        }
        questionDTO.setOptionDTO(optionDTOs);

        if (questionDTO == null) {
            throw new RuntimeException("at least one question has to be defined");
        }

        return questionDTO;
    }


    @Override
    public void save(QuestionDTO dto) {
        Question entity = new Question();
        entity.setName(dto.getName());
        entity.setId(dto.getId());
        for (QuestionOptionDTO optionDTO : dto.getOptionDTO()) {
            entity.addOption(convert(optionDTO));
        }
        questionRepository.save(entity);
    }


    private QuestionOption convert(QuestionOptionDTO dto) {
        QuestionOption entity = new QuestionOption();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }


    private CSVReader openCSV(final InputStream input) throws IOException {
        return new CSVReader(new InputStreamReader(input, "UTF8"), ';', '"');
    }

    // .stream().map(u -> convert(u, rejected)).collect(toList());
    @Override
    public List<QuestionDTO> findAll() {
        List<Question> questionList = questionRepository.findAll();
        return questionList.stream().map(q -> convert(q)).collect(Collectors.toList());
    }

    private QuestionDTO convert(Question entity) {
        QuestionDTO dto = new QuestionDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setOptionDTO(convert(entity.getQuestionOptions()));
        return dto;
    }

    private List<QuestionOptionDTO> convert(List<QuestionOption> options) {
        return options.stream().map(o -> convert(o)).collect(Collectors.toList());

    }

    private QuestionOptionDTO convert(QuestionOption entity) {
        QuestionOptionDTO dto = new QuestionOptionDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        return dto;
    }

}
