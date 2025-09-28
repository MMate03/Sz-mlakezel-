package com.example.szamlakezelo.controller;

import com.example.szamlakezelo.model.User;
import com.example.szamlakezelo.model.Role;
import com.example.szamlakezelo.service.UserService;
import com.example.szamlakezelo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/updateRoles/{id}")
    public String updateUserRoles(@PathVariable Long id, @RequestParam List<String> roles) {
        userService.updateUserRoles(id, roles);
        return "redirect:/admin/users";
    }
}
