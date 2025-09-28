package com.example.szamlakezelo.controller;

import com.example.szamlakezelo.model.User;
import com.example.szamlakezelo.service.LoginAttemptService;
import com.example.szamlakezelo.service.RoleService;
import com.example.szamlakezelo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

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
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
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
            return "register";
        }
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Felhasználó nem található"));
        model.addAttribute("username", user.getUsername());
        model.addAttribute("loginDate", user.getLoginDate());
        model.addAttribute("roles", user.getRole());
        return "home";
    }



    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/debugRoles")
    @ResponseBody
    public String debugRoles(Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Felhasználó nem található"));
        return "Jogosultságok: " +
                user.getRole().stream()
                        .map(r -> r.getName().name())
                        .collect(Collectors.joining(", "));
    }
}

