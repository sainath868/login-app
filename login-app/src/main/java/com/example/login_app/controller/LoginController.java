package com.example.login_app.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        // If the user is authenticated, show their profile details on the home page.
        if (oauth2User != null) {
            model.addAttribute("name", resolveName(oauth2User));
            model.addAttribute("email", resolveEmail(oauth2User));
            model.addAttribute("authenticated", true);
        } else {
            // For guests, render a home page with OAuth2 sign-in options.
            model.addAttribute("authenticated", false);
        }

        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        // Custom login page with buttons for Google and GitHub OAuth2 providers.
        return "login";
    }

    @GetMapping("/success")
    public String successPage(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        // After successful OAuth2 login, expose name/email and render a success view.
        model.addAttribute("name", resolveName(oauth2User));
        model.addAttribute("email", resolveEmail(oauth2User));
        return "success";
    }

    private String resolveName(OAuth2User oauth2User) {
        // Name claim differs by provider: Google uses "name", GitHub commonly uses "login".
        String name = oauth2User.getAttribute("name");
        if (name == null || name.isBlank()) {
            name = oauth2User.getAttribute("login");
        }
        return name != null ? name : "Unknown User";
    }

    private String resolveEmail(OAuth2User oauth2User) {
        // Email may be absent from GitHub unless it's public / available via scope.
        String email = oauth2User.getAttribute("email");
        return (email != null && !email.isBlank()) ? email : "Email not provided";
    }
}
