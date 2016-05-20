package com.elite.dto;


import com.elite.domain.Children;

import java.util.ArrayList;
import java.util.List;

public class ParentDTO extends DTO {

    private String name;
    private String phoneNo;
    private String email;
    private String address;
    private int noOfChildren;
    private UserDTO userDTO;
    private QuestionDTO questionDTO;
    private Integer priority;
    private String standard;
    private String schoolName;
    private List<ChildrenDTO> childrenDTOs;

    public ParentDTO() {
        this.childrenDTOs = new ArrayList<>();
        this.childrenDTOs.add(new ChildrenDTO("Level1", "Elite"));
        this.childrenDTOs.add(new ChildrenDTO("Level2", "Elite2"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
    }


    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }


    public QuestionDTO getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(QuestionDTO questionDTO) {
        this.questionDTO = questionDTO;
    }


    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<ChildrenDTO> getChildrenDTOs() {
        return childrenDTOs;
    }

    public void setChildrenDTOs(List<ChildrenDTO> childrenDTOs) {
        this.childrenDTOs = childrenDTOs;
    }
}
