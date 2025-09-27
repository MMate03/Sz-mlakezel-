package com.example.szamlakezelo.controller;

import com.example.szamlakezelo.model.User;
import com.example.szamlakezelo.service.LoginAttemptService;
import com.example.szamlakezelo.service.RoleService;
import com.example.szamlakezelo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private LoginAttemptService loginAttemptService;

    @Autowired
    public void setLoginAttemptService(LoginAttemptService loginAttemptService) {this.loginAttemptService = loginAttemptService;}
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {
        // Ha van hibaüzenet a sessionben → hozzáadjuk a modelhez
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage"); // ha szeretnéd, hogy csak egyszer jelenjen meg
        }

        String username = (String) request.getSession().getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
        }

        if (username != null && loginAttemptService.isCaptchaRequired(username)) {
            model.addAttribute("showCaptcha", true);
        }

        return "login";
    }





    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRolesNoAdmin());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {

        try {
            userService.register(user);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getRolesNoAdmin());
            return "register"; // visszatérés a regisztrációs oldalra
        }
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        User user=userService.findByUsername(principal.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("loginDate", user.getLoginDate());
        model.addAttribute("roles", user.getRole());
        return "home";
    }



    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}

