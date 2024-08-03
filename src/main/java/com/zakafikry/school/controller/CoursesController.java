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

//    @PostMapping("")
//    public String post(@RequestBody CourseDto courseDto, Model model) {
//        System.out.println("POST COURSE");
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        courseDto.setUsername(username);
//        coursesService.registerCourse(courseDto);
//        model.addAttribute("message", "Course created successfully");
//        return "redirect:/course";  // Redirect to the course list page
//    }
}
