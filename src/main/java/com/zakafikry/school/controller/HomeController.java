package com.zakafikry.school.controller;

import com.zakafikry.school.dto.UserRegistrationDto;
import com.zakafikry.school.entity.Users;
import com.zakafikry.school.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UsersService userService;

    @GetMapping("/")
    public String dashboard(Model model, HttpServletRequest request) {
        model.addAttribute("currentPath", request.getRequestURI());
        return "dashboard/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(UserRegistrationDto userRegistrationDto, RedirectAttributes redirectAttributes) {

        boolean isUserExist = userService.userExists(userRegistrationDto.getUsername());
        if (isUserExist) {
            redirectAttributes.addFlashAttribute("registrationError", "Username already exists");
        } else {
            userService.registerUser(userRegistrationDto);
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful. Please login.");
        }
        return "redirect:/login";
    }

}