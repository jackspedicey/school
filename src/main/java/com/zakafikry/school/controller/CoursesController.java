package com.zakafikry.school.controller;

import com.zakafikry.school.service.CoursesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course")
public class CoursesController {

    @Autowired
    private CoursesService coursesService;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("currentPath", request.getRequestURI());
        return "courses/index";
    }
}
