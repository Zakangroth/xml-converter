package com.zakangroth.xmlconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String redirectOnLoginPage() {
        return "redirect:/home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage(ModelMap homeModel) {
        homeModel.put("currentDate", new Date());
        return "home";
    }
}