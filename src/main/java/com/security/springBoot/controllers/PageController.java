package com.security.springBoot.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.security.springBoot.models.Role;
import com.security.springBoot.models.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        roles.add(new Role("ROLE_ADMIN"));
        model.addAttribute("roleList", roles);
        return "admin";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    @PostMapping("logout")
    public String logoutURL(){
        return "redirect:/logout";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
