package com.elite.initialization.impl;


import com.elite.initialization.InitializationService;
import com.elite.repository.ParentRepository;
import com.elite.repository.QuestionRepository;
import com.elite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Profile("default")
public class QuestionsInitializationService implements InitializationService {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void doImport() throws Exception {
        if (questionRepository.count() == 0) {
            final InputStream questions = resourceLoader.getResource("classpath:Question_Option.csv").getInputStream();
            questionService.importQuestionCSV(questions);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
