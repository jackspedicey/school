package com.zakafikry.school.controller;

import com.zakafikry.school.entity.Students;
import com.zakafikry.school.entity.Teachers;
import com.zakafikry.school.repository.TeachersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/teacher")
public class TeachersController {

    @Autowired
    private TeachersRepository tRepository;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Teachers> opStudent = tRepository.findByUsername(username);

        opStudent.ifPresent(teacher -> model.addAttribute("teacher", teacher));

        model.addAttribute("currentPath", request.getRequestURI());

        return "teachers/index";
    }

    @PostMapping("/update")
    public String updateTeacher(@RequestBody Teachers teacher, RedirectAttributes redirectAttributes) {
        Optional<Teachers> optTeacher = tRepository.findById(teacher.getId());
        if (optTeacher.isPresent()) {
            Teachers updatedTeacher = optTeacher.get();
            updatedTeacher.setFirstName(teacher.getFirstName());
            updatedTeacher.setLastName(teacher.getLastName());
            updatedTeacher.setEmail(teacher.getEmail());
            updatedTeacher.setBirthDate(teacher.getBirthDate());
            updatedTeacher.setAddress(teacher.getAddress());

            tRepository.save(updatedTeacher);

            redirectAttributes.addFlashAttribute("message", "Teacher updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Teacher not found");

        }

        return "redirect:/teacher";
    }
}
