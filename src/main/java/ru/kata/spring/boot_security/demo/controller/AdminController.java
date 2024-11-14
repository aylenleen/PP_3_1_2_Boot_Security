package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
@EnableAutoConfiguration
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String showAdminPage() {
        return "admin-page";
    }

    @GetMapping(value ="/all-users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all-users";
    }

    @GetMapping(value ="/addUser")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        return "user-info";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/all-users";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/all-users";
    }

    @GetMapping(value ="/updateInfo")
    public String updateUser(@RequestParam("userId") int id, Model model) {
        model.addAttribute("action", "update");
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        return "user-info";
    }

    @GetMapping(value ="/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        User user = userService.getUserById(id);
        userService.delete(user);
        return "redirect:/admin/all-users";
    }
}
