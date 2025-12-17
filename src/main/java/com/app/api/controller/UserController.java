package com.app.api.controller;

import com.app.api.dto.user.AuthRequest;
import com.app.api.entity.UserInfo;
import com.app.api.security.service.JwtService;
import com.app.api.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserEmail(),authRequest.getPassword())
        );

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest);
        }else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }
}
