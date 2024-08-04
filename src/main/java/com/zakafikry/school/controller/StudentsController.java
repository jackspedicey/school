package com.zakafikry.school.controller;

import com.zakafikry.school.entity.Students;
import com.zakafikry.school.repository.StudentsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentsController {

    @Autowired
    private StudentsRepository studentsRepository;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Students> opStudent = studentsRepository.findByUsername(username);

        opStudent.ifPresent(students -> model.addAttribute("student", students));

        model.addAttribute("currentPath", request.getRequestURI());

        return "students/index";
    }

    @PostMapping("/update")
    public String updateStudent(@RequestBody Students student, RedirectAttributes redirectAttributes) {
        System.out.println("Updating student : " + student.toString());
        Optional<Students> optStudent = studentsRepository.findById(student.getId());
        if (optStudent.isPresent()) {
            Students updatedStudent = optStudent.get();
            updatedStudent.setFirstName(student.getFirstName());
            updatedStudent.setLastName(student.getLastName());
            updatedStudent.setEmail(student.getEmail());
            updatedStudent.setBirthDate(student.getBirthDate());
            updatedStudent.setAddress(student.getAddress());

            studentsRepository.save(updatedStudent);

            redirectAttributes.addFlashAttribute("message", "Student updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Student not found");
        }
        return "redirect:/student";
    }
}
