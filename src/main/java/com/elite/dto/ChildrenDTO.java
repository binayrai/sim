package com.elite.dto;

import com.elite.domain.Parent;

/**
 * Created by binaychap on 2/24/2016.
 */
public class ChildrenDTO  extends DTO{


    private String standard;
    private String schoolName;
    private ParentDTO parentDTO;

    public ChildrenDTO() {

    }

    public ChildrenDTO(String standard, String schoolName) {
        this.standard = standard;
        this.schoolName = schoolName;
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

    public ParentDTO getParentDTO() {
        return parentDTO;
    }

    public void setParentDTO(ParentDTO parentDTO) {
        this.parentDTO = parentDTO;
    }
}
