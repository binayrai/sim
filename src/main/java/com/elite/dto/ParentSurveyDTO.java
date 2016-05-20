package com.elite.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by binaychap on 2/24/2016.
 */
public class ParentSurveyDTO extends DTO {
    private ParentDTO parentDTO;
    private QuestionDTO questionDTO;
    private List<QuestionOptionDTO> optionDTO;

    public ParentSurveyDTO() {
        this.parentDTO = new ParentDTO();
        this.questionDTO = new QuestionDTO();
        this.optionDTO = new ArrayList<QuestionOptionDTO>();

    }

    public ParentDTO getParentDTO() {
        return parentDTO;
    }

    public void setParentDTO(ParentDTO parentDTO) {
        this.parentDTO = parentDTO;
    }

    public QuestionDTO getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(QuestionDTO questionDTO) {
        this.questionDTO = questionDTO;
    }



    public List<QuestionOptionDTO> getOptionDTO() {
        return optionDTO;
    }

    public void setOptionDTO(List<QuestionOptionDTO> optionDTO) {
        this.optionDTO = optionDTO;
    }
}
