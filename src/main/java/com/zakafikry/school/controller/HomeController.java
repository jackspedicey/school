package com.zakafikry.school.controller;

import com.zakafikry.school.dto.UserRegistrationDto;
import com.zakafikry.school.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private UsersService userService;

//    @Autowired
//    AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String dashboard(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                System.out.println(username);

                boolean isStudent = authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_STUDENT"));
                boolean isTeacher = authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_TEACHER"));
                System.out.println(isTeacher);
                System.out.println(isStudent);
            }
        }
        assert authentication != null;
        System.out.println("dashboard: " + authentication.toString());
        model.addAttribute("currentPath", request.getRequestURI());
        return "dashboard/index"; // Adjust this to your actual template path
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

//    @PostMapping("/login")
//    public String processLogin(UserRegistrationDto userRegistrationDto, Model model) {
//        System.out.println("Post LOGIN");
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userRegistrationDto.getUsername(), userRegistrationDto.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        return "redirect:/";
//    }

    @PostMapping("/register")
    public String registerUser(UserRegistrationDto userRegistrationDto, Model model) {
        System.out.println("Post REGISTER");
        userService.registerUser(userRegistrationDto);
        return "redirect:/";
    }
}