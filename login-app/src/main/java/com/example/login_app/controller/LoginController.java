package com.example.login_app.controller;

import com.example.login_app.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    // LOGIN PAGE
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,HttpSession session) {

        if(username.isBlank()||password.isBlank()){
            model.addAttribute("error","\"Username and Password cannot be empty");
            return "login";
        }


        if (loginService.validate(username, password)) {
            session.setAttribute("loggedInUser", username);
            return "redirect:/success";
        } else {
            model.addAttribute("error", "Invalid login");
            return "login";
        }
    }
    // SUCCESS PAGE (Protected manually)
    @GetMapping("/success")
    public String success(HttpSession session) {

        //  no session → go to login
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        return "success";
    }
    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        // ❌ destroy session
        session.invalidate();

        return "redirect:/login";}
    // REGISTER PAGE
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        if (username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "All fields are required");
            return "register";
        }
        if(password.length()<6){
            model.addAttribute("error","Password must be at least 6 characters");
            return "register";
        }
        if (!password.matches(".*[a-zA-Z].*")) {
            model.addAttribute("error", "Password must contain at least one letter");
            return "register";
        }

        // 🔴 NUMBER CHECK (0-9)
        if (!password.matches(".*[0-9].*")) {
            model.addAttribute("error", "Password must contain at least one number");
            return "register";
        }

        boolean result = loginService.register(username, password);

        if (result) {
            model.addAttribute("msg", "Registration successful! Please login.");
            return "login";
        } else {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
    }
}

























