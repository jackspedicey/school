package com.zakafikry.school.controller.rest;

import com.zakafikry.school.dto.CourseDTO;
import com.zakafikry.school.datatable.Specifications;
import com.zakafikry.school.dto.DataTablesInput;
import com.zakafikry.school.dto.DataTablesOutput;
import com.zakafikry.school.entity.Courses;
import com.zakafikry.school.repository.CoursesRepository;
import com.zakafikry.school.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CoursesRestController {

    @Autowired
    private CoursesService courseService;

    @Autowired
    private CoursesRepository coursesRepository;

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseDetails(@PathVariable Long courseId) {
        Optional<Courses> c = coursesRepository.findById(courseId);

        if (c.isPresent()) {
            CourseDTO dto = courseService.convertToDTO(c.get());
            return ResponseEntity.ok().body(dto);
        } else {
            return ResponseEntity.badRequest().body("Course not found");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDto) {
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
    public DataTablesOutput<CourseDTO> dataTableCourses(@RequestBody DataTablesInput input) {
        Specification<Courses> spec = Specifications.courseWithSearch(input.getSearchValue());
        return courseService.getCoursesDataTable(input, spec);
    }

    @PostMapping("/datatable/{teacherId}")
    public DataTablesOutput<CourseDTO> getCourseDataTable(@PathVariable Long teacherId, @RequestBody DataTablesInput request) {
        Specification<Courses> spec = Specifications.courseWithSearchAndTeacher(request.getSearchValue(), teacherId);
        return courseService.getCoursesDataTable(request, spec);
    }

}