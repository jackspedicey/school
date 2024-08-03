package com.zakafikry.school.controller.rest;

import com.zakafikry.school.dto.CourseDTO;
import com.zakafikry.school.datatable.Specifications;
import com.zakafikry.school.dto.DataTablesInput;
import com.zakafikry.school.dto.DataTablesOutput;
import com.zakafikry.school.entity.Courses;
import com.zakafikry.school.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
public class CoursesRestController {

    @Autowired
    private CoursesService courseService;

    @PostMapping("")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDto) {
        System.out.println("POST COURSE : " + courseDto.toString());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        courseDto.setUsername(username);

        try {
            courseService.registerCourse(courseDto);
            return ResponseEntity.ok().body("Course created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating course: " + e.getMessage());
        }
    }

    @PostMapping("/datatable")
    public DataTablesOutput<CourseDTO> getCoursesForDataTable(@RequestBody DataTablesInput input) {
        Specification<Courses> spec = Specifications.courseWithSearch(input.getSearchValue());
        return courseService.getCoursesForDataTable(input, spec);
    }
}