package com.elite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    public static final String HOME = "/home";
    public static final String LOGIN = "/login";

    @RequestMapping("/")
    public ModelAndView main(ModelMap model) {
        model.put("title", "home");
        return new ModelAndView("home", model);
    }

    @RequestMapping(LOGIN)
    public String login() {
        return "login";
    }

    @RequestMapping(HOME)
    public String mains() {
        return "home";
    }


}
