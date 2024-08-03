package com.zakafikry.school.controller;

import com.zakafikry.school.dto.UserRegistrationDto;
import com.zakafikry.school.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String registerUser(UserRegistrationDto userRegistrationDto, Model model) {
        System.out.println("Post REGISTER");
        userService.registerUser(userRegistrationDto);
        return "redirect:/login";
    }

}