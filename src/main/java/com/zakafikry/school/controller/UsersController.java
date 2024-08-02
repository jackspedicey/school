package com.zakafikry.school.controller;

import com.google.gson.Gson;
import com.zakafikry.school.entity.Users;
import com.zakafikry.school.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;


    @GetMapping("/")
    public String index(Model model) {
        List<Users> data = usersRepository.findAll();
        String json = new Gson().toJson(data);
        model.addAttribute("list", json);

        return "users/index";
    }

    @GetMapping("/detail")
    public String findUser(HttpServletRequest req, Model model) {
        Boolean isEdit = req.getParameter("edit") != null;

        if (req.getParameter("id") != null) {
            Long id = Long.valueOf(req.getParameter("id"));
            Users users = usersRepository.findById(id).get();
            model.addAttribute("users", users);
        } else {
            model.addAttribute("users", new Users());
        }

        model.addAttribute("edit", isEdit);
        return "users/detail";
    }

    @GetMapping("/add")
    public String addUser(HttpServletRequest req, Model model) {
        model.addAttribute("users", new Users());
        return "users/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Users users, RedirectAttributes attributes) {
        Optional<Users> optUser = usersRepository.findByUsername(users.getUsername());
        if (optUser.isPresent()) {
            if (users.getId() == null && users.getUsername().equals(optUser.get().getUsername())) {
                attributes.addFlashAttribute("message", "Username must be unique");
                return "redirect:/users";
            }
        }

        usersRepository.save(users);
        return "redirect:/users";
    }

    @GetMapping("/req/delete")
    public String reqDelete(HttpServletRequest req, Model model) {
        if (req.getParameter("id") != null) {
            Long id = Long.valueOf(req.getParameter("id"));
            Users users = usersRepository.findById(id).get();
            model.addAttribute("users", users);
        }

        return "users/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Users users) {
        usersRepository.delete(users);
        return "redirect:/users";
    }

}
