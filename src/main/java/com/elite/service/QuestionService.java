package com.elite.service;

import com.elite.dto.QuestionDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by binaychap on 2/26/2016.
 */
public interface QuestionService {



    void importQuestionCSV(InputStream questions) throws IOException;

    void save(QuestionDTO dto);

    List<QuestionDTO> findAll();


}
