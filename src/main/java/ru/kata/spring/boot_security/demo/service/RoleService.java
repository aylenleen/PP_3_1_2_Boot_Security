package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    List<String> getRolesNames(User user);
    public void addNewRole(Role role);
    public Role findByName(String roleName);
}
