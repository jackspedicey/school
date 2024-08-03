package com.zakafikry.school.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {

    @GetMapping("")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("currentPath", request.getRequestURI());
        return "enrollment/index";
    }

}
