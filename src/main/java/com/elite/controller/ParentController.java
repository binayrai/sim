package com.elite.controller;

import com.elite.WebMessage;
import com.elite.domain.Parent;
import com.elite.domain.ParentSurvey;
import com.elite.dto.*;
import com.elite.dto.enums.UserRole;
import com.elite.repository.ParentSurveyRepository;
import com.elite.service.ParentService;
import com.elite.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;


/**
 * Created by binaychap on 3/5/2016.
 */
@Controller
@RequestMapping(ParentController.PARENT)
public class ParentController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private MessageSource messageSource;


    public static final String PARENT = "parent";
    private static final String LIST = "/list";
    private static final String LISTWITHPAGING = LIST + "/{pageNumber}";
    private static final String NEW = "/new";
    private static final String REDIRECT = "redirect:/";
    private static final String PAGE_NUMBER = "/1";
    private static final int PAGE_SIZE = 10;
    private static final String VIEW = "/view";


    @RequestMapping
    public String user() {
        return REDIRECT + PARENT + LIST + PAGE_NUMBER;
    }

    @RequestMapping(value = LIST, method = RequestMethod.GET)
    public String list() {
        return REDIRECT + PARENT + LIST + PAGE_NUMBER;
    }

    @Transactional(readOnly = true)
    @Secured(UserRole.Role.ROLE_ADMIN)
    @RequestMapping(value = LISTWITHPAGING, method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, @PathVariable Integer pageNumber,
                             ParentSearchRequest parentSearchRequest) {
        parentSearchRequest.setPage(pageNumber - 1);

        PagedResultDTO<ParentDTO> page = parentService.list(parentSearchRequest);
        ModelAndView modelAndView = new ModelAndView(PARENT + LIST);

        modelAndView.addObject("paginationDTO", new PaginationDTO(PAGE_SIZE, page.getTotalCount(), pageNumber,
                "/" + PARENT + LISTWITHPAGING, request.getQueryString()));
        modelAndView.addObject("parentDTOS", page.getElements());
        return modelAndView;
    }

    @RequestMapping(value = NEW, method = RequestMethod.GET)
    public ModelAndView newUser() {
        ModelAndView modelAndView = new ModelAndView(PARENT + NEW);
        modelAndView.addObject("parentDTO", getParent());
        return modelAndView;
    }

    @RequestMapping(value = NEW, method = RequestMethod.POST)
    public String editFinished(@Valid ParentDTO parentDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            return PARENT + NEW;
        }

        parentService.save(parentDTO);
        redirectAttributes.addFlashAttribute(WebMessage.INFO, "Parent survey saved successfully");

        return REDIRECT + PARENT + NEW;
    }

    @Transactional(readOnly = true)
    @Secured(UserRole.Role.ROLE_ADMIN)
    @RequestMapping(value = VIEW, method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView(PARENT + VIEW);
        modelAndView.addObject("parentSurveyDTO", parentService.findAllByParentId(id));
        return modelAndView;
    }


    private ParentDTO getParent() {
        ParentDTO dto = new ParentDTO();

        List<QuestionDTO> dtos = questionService.findAll();
        //todo : only one question set,if there are more then have to refactored.
        for (QuestionDTO questionDTO : dtos) {
            dto.setQuestionDTO(questionDTO);
        }
        return dto;

    }


}
