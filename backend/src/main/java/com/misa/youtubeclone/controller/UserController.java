package com.misa.youtubeclone.controller;

import com.misa.youtubeclone.model.exceptions.UserRegistrationFailedException;
import com.misa.youtubeclone.service.UserRegistrationService;
import com.misa.youtubeclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    @GetMapping("/register")
    @SuppressWarnings("unused")
    public String register(Authentication auth){
        try {
            var jwt = (Jwt) auth.getPrincipal();
            userRegistrationService.registerUser(jwt.getTokenValue());
            return "User registration successful";
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserRegistrationFailedException("Error occurred while registering the user.", e);
        }
    }

    @PostMapping("subscribe/{userId}")
    @SuppressWarnings("unused")
    public boolean subscribeUser(@PathVariable String userId){
        userService.subscribe(userId);
        return true;
    }

    @PostMapping("unsubscribe/{userId}")
    @SuppressWarnings("unused")
    public boolean unSubscribeUser(@PathVariable String userId){
        userService.unSubscribe(userId);
        return true;
    }
}
