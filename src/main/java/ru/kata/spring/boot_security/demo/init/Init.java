package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class Init {

    private UserService userService;

    @Autowired
    public Init(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        roles.add(new Role("ROLE_ADMIN"));
        User admin = new User("newadmin", "admin", "Admin",
                "Adminov", "admin@mail.ru", roles);
        User user = new User("newuser", "user", "User",
                "Userov", "user@mail.ru", Collections.singleton(new Role("ROLE_USER")));
        userService.add(admin);
        userService.add(user);
    }
}
