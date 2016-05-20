package com.elite.service.impl;

import com.elite.domain.*;
import com.elite.dto.*;
import com.elite.repository.ParentRepository;
import com.elite.repository.ParentSurveyRepository;
import com.elite.repository.QuestionOptionRepository;
import com.elite.repository.UserRepository;
import com.elite.service.ParentService;
import com.elite.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ParentSurveyRepository surveyRepository;
    @Autowired
    private QuestionOptionRepository optionRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public PagedResultDTO<ParentDTO> list(ParentSearchRequest parentSearchRequest) {
        Page<Parent> page = parentRepository.findAllByName(
                "%" + parentSearchRequest.getName() + "%",
                new PageRequest(parentSearchRequest.getPage(), parentSearchRequest.getSize()));
        List<ParentDTO> dtos = page.getContent().stream().map(u -> convert(u)).collect(toList());
        return new PagedResultDTO<>(dtos, page.getTotalElements());

    }

    private ParentDTO convert(Parent entity) {
        ParentDTO dto = new ParentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setNoOfChildren(entity.getNoOfChildren());
        dto.setPhoneNo(entity.getPhoneNo());
        return dto;

    }

    private List<ChildrenDTO> convertEntityToDTO(List<Children> childrens) {
        List<ChildrenDTO> childrenDTOs = new ArrayList<>();
        for (Children children : childrens) {
            childrenDTOs.add(convert(children));
        }
        return childrenDTOs;


    }


    private ChildrenDTO convert(Children entity) {
        ChildrenDTO dto = new ChildrenDTO();
        dto.setId(entity.getId());
        dto.setSchoolName(entity.getSchoolName());
        dto.setStandard(entity.getStandard());
        dto.setParentDTO(convert(entity.getParent()));
        return dto;
    }

    private Children convert(ChildrenDTO dto) {
        Children entity = new Children();
        entity.setId(dto.getId());
        entity.setSchoolName(dto.getSchoolName());
        entity.setStandard(dto.getStandard());
        return entity;
    }


    @Override
    public void save(ParentDTO dto) {
        Parent entity = new Parent();
        if (dto.getId() > 0) {
            entity = parentRepository.getOne(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhoneNo(dto.getPhoneNo());
        entity.setEmail(dto.getEmail());
        entity.setNoOfChildren(dto.getNoOfChildren());
        entity.setCreatedDate(new Date());
        entity.setLastModifiedDate(new Date());
        User user = userRepository.findOne(securityService.getCurrentUser().getId());
        entity.setCreatedBy(user);
        Parent parent = parentRepository.save(entity);
        saveParentSurvey(dto, parent);
    }

    private void saveParentSurvey(ParentDTO parentDTO, Parent parent) {
        ParentSurvey parentSurvey;
        for (Map.Entry<String, String> entry : parentDTO.getQuestionDTO().getOptionPriority().entrySet()) {
            parentSurvey = new ParentSurvey();
            Long optionId = Long.parseLong(entry.getKey());
            String test = entry.getValue();
            Long questionHierarchy = Long.parseLong(test.trim().toString());
            QuestionOption questionOption = optionRepository.findOne(optionId);
            parentSurvey.setQuestion(questionOption.getQuestion());
            parentSurvey.setParent(parent);
            parentSurvey.setOption(questionOption);
            parentSurvey.setQuestionHierarchy(questionHierarchy);

            surveyRepository.save(parentSurvey);
        }

    }

    @Override
    public ParentSurveyDTO findAllByParentId(long id) {
        ParentSurveyDTO surveyDTO;
        List<ParentSurvey> survey = surveyRepository.findAllByByParent(id);
        surveyDTO = convert(survey.get(0));
        for (ParentSurvey parentSurvey : survey) {
            surveyDTO.getOptionDTO().add(convert(parentSurvey.getOption(), parentSurvey.getQuestionHierarchy()));
        }
        return surveyDTO;
    }

    private QuestionOptionDTO convert(QuestionOption entity, long hierarchy) {
        QuestionOptionDTO dto = new QuestionOptionDTO();
        dto.setName(entity.getName());
        dto.setQuestionHierarchy(hierarchy);
        return dto;
    }

    private ParentSurveyDTO convert(ParentSurvey entity) {
        ParentSurveyDTO dto = new ParentSurveyDTO();
        dto.getParentDTO().setName(entity.getParent().getName());
        dto.getParentDTO().setPhoneNo(entity.getParent().getPhoneNo());
        dto.getParentDTO().setEmail(entity.getParent().getEmail());
        dto.getParentDTO().setNoOfChildren(entity.getParent().getNoOfChildren());
        dto.getParentDTO().setAddress(entity.getParent().getAddress());
        dto.getParentDTO().setStandard(entity.getParent().getStandard());
        dto.getParentDTO().setSchoolName(entity.getParent().getSchoolName());
        dto.getQuestionDTO().setName(entity.getQuestion().getName());
        return dto;
    }

    @Override
    public ParentDTO get(long id) {
        Parent parent = parentRepository.findOne(id);
        return convert(parent);
    }

    @Override
    public boolean deleteParent(long parentId) {
        return false;
    }
}
