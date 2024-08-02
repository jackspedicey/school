package com.zakafikry.school.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentsController {
    @GetMapping("")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("currentPath", request.getRequestURI());
        return "students/index";
    }
}
