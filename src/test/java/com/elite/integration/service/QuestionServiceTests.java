package com.elite.integration.service;

import com.elite.domain.Question;
import com.elite.domain.QuestionOption;
import com.elite.dto.ParentDTO;
import com.elite.dto.QuestionDTO;
import com.elite.dto.QuestionOptionDTO;
import com.elite.integration.BaseIntegrationTest;
import com.elite.repository.QuestionOptionRepository;
import com.elite.repository.QuestionRepository;
import com.elite.service.QuestionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binaychap on 3/4/2016.
 */
@Transactional
public class QuestionServiceTests extends BaseIntegrationTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    QuestionDTO questionDTO;

    @Before
    public void setup() {
        questionDTO = new QuestionDTO();
        questionRepository.deleteAll();
    }

    @Test
    public void saveTest() {
        questionDTO.setName("question");
        questionDTO.addOption(new QuestionOptionDTO("test 1"));
        questionDTO.addOption(new QuestionOptionDTO("test 2"));
        questionService.save(questionDTO);
        List<Question> list = questionRepository.findAll();
        Assert.assertEquals(1, list.size());


    }

}
