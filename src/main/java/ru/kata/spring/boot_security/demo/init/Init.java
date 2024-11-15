package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class Init {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {

        Role roleUser = roleService.findByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role("ROLE_USER");
            roleService.addNewRole(roleUser);
        }

        Role roleAdmin = roleService.findByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role("ROLE_ADMIN");
            roleService.addNewRole(roleAdmin);
        }

        User admin = userService.getUserByUsername("admin");
        if (admin == null) {
            admin = new User("admin", "admin", "Admin",
                    "Adminov", "admin@mail.ru", Set.of(roleAdmin, roleUser));
            userService.add(admin);
        }

        User user = userService.getUserByUsername("user");
        if (user == null) {
            user = new User("user", "user", "User",
                    "Userov", "user@mail.ru", Set.of(roleUser));
            userService.add(user);
        }

    }
}
