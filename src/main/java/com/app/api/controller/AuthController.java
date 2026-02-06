package com.app.api.controller;

import com.app.api.dto.user.AuthRequest;
import com.app.api.dto.user.AuthResponse;
import com.app.api.dto.user.CreateUserRequest;
import com.app.api.entity.User;
import com.app.api.security.service.AuthService;
import com.app.api.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest){
        return authService.authenticate(authRequest);
    }

    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody CreateUserRequest user) {
        return userService.addUser(user);
    }
}
