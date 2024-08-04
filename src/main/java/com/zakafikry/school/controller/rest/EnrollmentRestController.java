package com.zakafikry.school.controller.rest;

import com.zakafikry.school.datatable.Specifications;
import com.zakafikry.school.dto.DataTablesInput;
import com.zakafikry.school.dto.DataTablesOutput;
import com.zakafikry.school.dto.EnrollmentDTO;
import com.zakafikry.school.entity.Enrollment;
import com.zakafikry.school.entity.Students;
import com.zakafikry.school.repository.StudentsRepository;
import com.zakafikry.school.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentRestController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentsRepository studentsRepository;

    @PostMapping("/datatable")
    public DataTablesOutput<EnrollmentDTO> dataTableEnrollment(@RequestBody DataTablesInput input) {
        Specification<Enrollment> spec = Specifications.enrollmentWithSearch(input.getSearchValue());
        return enrollmentService.getEnrollmentDataTable(input, spec);
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Long courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Students> optStudent = studentsRepository.findByUsername(username);

        if (optStudent.isPresent()) {
            enrollmentService.enrollStudent(courseId, optStudent.get().getId());
            return ResponseEntity.ok().body("Course enrolled successfully");
        } else {
            return ResponseEntity.badRequest().body("Error enrolling course");
        }
    }
}

