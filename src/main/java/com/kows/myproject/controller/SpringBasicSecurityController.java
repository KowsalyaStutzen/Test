package com.kows.myproject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SpringBasicSecurityController {
    
    @GetMapping("welcome")
    public String welcome() {
        return "Haii, Welcome to spring boot!";
    }
    
}
