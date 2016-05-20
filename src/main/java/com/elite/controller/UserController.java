package com.elite.controller;

import com.elite.WebMessage;
import com.elite.controller.exceptions.EntityObjectNotFoundException;
import com.elite.dto.PagedResultDTO;
import com.elite.dto.PaginationDTO;
import com.elite.dto.UserDTO;
import com.elite.dto.UserSearchRequest;
import com.elite.dto.enums.UserRole;
import com.elite.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Controller
@RequestMapping(UserController.USER)
public class UserController {
    public static final String USER = "user";
    private static final String LIST = "/list";
    private static final String LISTWITHPAGING = LIST + "/{pageNumber}";
    private static final String NEW = "/new";
    private static final String EDIT = "/edit";
    private static final String REDIRECT = "redirect:/";
    private static final String DELETE = "/delete";
    private static final String PAGE_NUMBER = "/1";

    private static final int PAGE_SIZE = 10;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("title")
    public String getTitle() {
        return "users.title";
    }

    @RequestMapping
    public String user() {
        return REDIRECT + USER + LIST + PAGE_NUMBER;
    }

    @RequestMapping(value = LIST, method = RequestMethod.GET)
    public String list() {
        return REDIRECT + USER + LIST + PAGE_NUMBER;
    }

    @Transactional(readOnly = true)
    @Secured(UserRole.Role.ROLE_ADMIN)
    @RequestMapping(value = LISTWITHPAGING, method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, @PathVariable Integer pageNumber,
                             UserSearchRequest userSearchRequest) {
        userSearchRequest.setPage(pageNumber - 1);

        PagedResultDTO<UserDTO> page = userService.list(userSearchRequest);
        ModelAndView modelAndView = new ModelAndView(USER + LIST);

        modelAndView.addObject("paginationDTO", new PaginationDTO(PAGE_SIZE, page.getTotalCount(), pageNumber,
                "/" + USER + LISTWITHPAGING, request.getQueryString()));
        modelAndView.addObject("userDTOs", page.getElements());
        return modelAndView;
    }

    @Secured(UserRole.Role.ROLE_ADMIN)
    @RequestMapping(value = NEW, method = RequestMethod.GET)
    public ModelAndView newUser() {
        ModelAndView modelAndView = new ModelAndView(USER + EDIT);
        modelAndView.addObject("userDTO", new UserDTO());
        return modelAndView;
    }

    @Transactional(readOnly = true)
    @Secured(UserRole.Role.ROLE_ADMIN)
    @RequestMapping(value = EDIT, method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView(USER + EDIT);
        modelAndView.addObject("userDTO", getOrThrow(id));
        return modelAndView;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Secured(UserRole.Role.ROLE_ADMIN)
    @RequestMapping(value = EDIT, method = RequestMethod.POST)
    public String editFinished(@Valid UserDTO userDTO,
                               BindingResult bindingResult,
                               @RequestParam(value = "action") String action,
                               RedirectAttributes redirectAttributes) {
        if ("save".equals(action)) {
            if (userDTO.isChangePassword() || userDTO.getId() <= 0) {
                if (StringUtils.isEmpty(userDTO.getPassword()) || userDTO.getPassword().length() < 4 || userDTO
                        .getPassword().length() > 50) {
                    bindingResult.rejectValue("password", "password.size.numberparams", new Object[]{"4", "50"},
                            "password.size.numberparams");
                }
            }
            if (userDTO.isLocked() && StringUtils.isEmpty(userDTO.getLockReason())) {
                bindingResult.rejectValue("lockReason", "user.lock.reason.required");
            }


            if (bindingResult.hasErrors()) {
                return USER + EDIT;
            }

            userService.save(userDTO);

            redirectAttributes.addFlashAttribute(WebMessage.INFO,
                    messageSource.getMessage("user.save.success", new Object[]{userDTO.getUsername()},
                            "user.save.success", getLocale()));
        }
        return REDIRECT + USER + LIST + PAGE_NUMBER;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Secured(UserRole.Role.ROLE_ADMIN)
    @RequestMapping(value = DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id", required = true) Long id, RedirectAttributes redirectAttributes) {
        final UserDTO userDTO = getOrThrow(id);
        boolean delete = userService.deleteUser(id);
        if (delete) {
            redirectAttributes.addFlashAttribute(WebMessage.INFO,
                    messageSource.getMessage("user.delete.success", new Object[]{userDTO.getUsername()},
                            "user.delete.success", getLocale()));
        } else {
            redirectAttributes.addFlashAttribute(WebMessage.ERROR,
                    messageSource.getMessage("user.delete.fail", new Object[]{userDTO.getUsername()},
                            "user.delete.fail", getLocale()));
        }

        return REDIRECT + USER + LIST + PAGE_NUMBER;
    }






    private UserDTO getOrThrow(Long id) {
        UserDTO userDTO = userService.get(id);
        if (userDTO == null) {
            throw new EntityObjectNotFoundException(id);
        }
        return userDTO;
    }
}
