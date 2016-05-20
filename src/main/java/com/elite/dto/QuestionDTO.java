package com.elite.dto;

import com.elite.domain.QuestionOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by binaychap on 2/24/2016.
 */
public class QuestionDTO extends DTO {

    private String name;
    private List<QuestionOptionDTO> optionDTO;
    private HashMap<String, String> optionPriority = new HashMap<>();

    public QuestionDTO() {
    }

    public QuestionDTO(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionOptionDTO> getOptionDTO() {
        return optionDTO;
    }

    public void setOptionDTO(List<QuestionOptionDTO> optionDTO) {
        this.optionDTO = optionDTO;
    }

    public void addOption(QuestionOptionDTO optionDTO) {
        if (this.optionDTO == null) {
            this.optionDTO = new ArrayList<>();
        }
        this.optionDTO.add(optionDTO);
        optionDTO.setQuestionDTO(this);

    }


    public HashMap<String, String> getOptionPriority() {
        return optionPriority;
    }

    public void setOptionPriority(HashMap<String, String> optionPriority) {
        this.optionPriority = optionPriority;
    }
}
