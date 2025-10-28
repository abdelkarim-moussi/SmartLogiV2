package com.app.SmartLogiV2.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Spring Boot REST API!";
    }
}
