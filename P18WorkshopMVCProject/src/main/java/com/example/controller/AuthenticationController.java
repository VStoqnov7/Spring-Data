package com.example.controller;

import com.example.models.dto.RegistrationDTO;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String registerView(RegistrationDTO registrationDto) {
        return "user/register";
    }

    @PostMapping(value = "/users/register")
    public String doRegister(@Valid RegistrationDTO registrationDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        this.userService.register(registrationDto);
        return "user/login";
    }
}