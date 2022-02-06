package com.misa.youtubeclone.controller;

import com.misa.youtubeclone.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/register")
    @SuppressWarnings("unused")
    public String register(Authentication auth){
        try {
            var jwt = (Jwt) auth.getPrincipal();
            userRegistrationService.registerUser(jwt.getTokenValue());
            return "User registration successful";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while registering the user.", e);
        }
    }
}
