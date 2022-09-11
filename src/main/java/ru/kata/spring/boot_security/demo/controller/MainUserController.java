package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Controller
public class MainUserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String getAllUsers(@ModelAttribute("newuser") User user,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userService.getUserByName(authentication.getName());
        model.addAttribute("authuser", authUser);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "admin";
    }

    @PostMapping("/admin/saveNewUser")
    public String saveNewUsers(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/editUser")
    public String editUser(@ModelAttribute("edituser") User user){//User user) {
        userService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUserById(Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

}
