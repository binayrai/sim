package com.elite.dto;

import com.elite.domain.Question;

/**
 * Created by binaychap on 2/26/2016.
 */
public class QuestionOptionDTO extends DTO {
    private String name;
    private QuestionDTO questionDTO;

    private long questionHierarchy;

    public QuestionOptionDTO() {
    }



    public QuestionOptionDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public QuestionDTO getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(QuestionDTO questionDTO) {
        this.questionDTO = questionDTO;
    }

    public long getQuestionHierarchy() {
        return questionHierarchy;
    }

    public void setQuestionHierarchy(long questionHierarchy) {
        this.questionHierarchy = questionHierarchy;
    }
}
