package com.elite.integration.service;


import com.elite.domain.Parent;
import com.elite.dto.ChildrenDTO;
import com.elite.dto.ParentDTO;
import com.elite.dto.UserDTO;
import com.elite.dto.enums.UserRole;
import com.elite.integration.BaseIntegrationTest;
import com.elite.repository.ParentRepository;
import com.elite.service.ParentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;


public class ParentServiceTests extends BaseIntegrationTest {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ParentService parentService;
    ParentDTO dto;

    @Before
    public void setup() {
        dto = new ParentDTO();
        parentRepository.deleteAll();
    }


    @Test
    public void parentSave() throws Exception {
        saveParentAndChildren();
        Page<Parent> pages = parentRepository.findAllByName(dto.getName(), new PageRequest(0, 10));
        Assert.assertEquals(1, pages.getTotalElements());
        Assert.assertEquals(1, pages.getContent().size());
    }

    /*@Test
    public void parentFetch() {
        saveParentAndChildren();
        List<Parent> parents = parentRepository.findAllWithChildren();
        Assert.assertEquals(2, parents.size());
       // Assert.assertEquals(2, parents.get(0).getChildrens().size());
    }
*/
    private void saveParentAndChildren() {
        List<ChildrenDTO> list = new ArrayList<>();
        dto.setId(0);
        dto.setAddress("Ratopul 07");
        dto.setEmail("binaychap@gmail.com");
        dto.setPhoneNo("9849108491");
        dto.setName("Binay Rai");
        list.add(new ChildrenDTO("2", "Elite"));
        list.add(new ChildrenDTO("3", "Vijay"));
       // dto.setChildrens(list);
        parentService.save(dto);
    }


}
