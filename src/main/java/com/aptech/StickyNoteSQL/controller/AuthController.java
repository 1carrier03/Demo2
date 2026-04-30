package com.aptech.StickyNoteSQL.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.aptech.StickyNoteSQL.model.UserAccount;
import com.aptech.StickyNoteSQL.service.UserService;

import reactor.core.publisher.Mono;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new UserAccount());
        return "signup";
    }

    @PostMapping("/signup")
    public Mono<String> signup(@Valid @ModelAttribute("user") UserAccount user,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return Mono.just("signup");
        }

        return userService.registerUser(user)
                .map(savedUser -> "redirect:/signin?registered")
                .onErrorResume(ex -> {
                    model.addAttribute("error", ex.getMessage());
                    return Mono.just("signup");
                });
    }

    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/notes";
    }
}