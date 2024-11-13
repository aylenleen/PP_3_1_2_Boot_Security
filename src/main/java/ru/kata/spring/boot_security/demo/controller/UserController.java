package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

import static java.util.Arrays.stream;

@Controller
@EnableAutoConfiguration
public class UserController {

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

    @GetMapping(value ="/admin/all-users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all-users";
    }

    @GetMapping(value ="/admin/addUser")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        return "user-info";
    }

    @PostMapping(value = "/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/all-users";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/all-users";
    }

    @GetMapping(value ="/admin/updateInfo")
    public String updateUser(@RequestParam("userId") int id, Model model) {
        model.addAttribute("action", "update");
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        return "user-info";
    }

    @GetMapping(value ="/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        User user = userService.getUserById(id);
        userService.delete(user);
        return "redirect:/";
    }

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/user")
    public String userInfo(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        List<String> roleNames = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();
        model.addAttribute("roles", roleNames);
        return "users-view";
    }

    @GetMapping("/admin")
    public String adminInfo() {
        return "admin-page";
    }
}
