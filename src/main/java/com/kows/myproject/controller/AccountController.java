package com.kows.myproject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AccountController {
 
    @GetMapping("accountDetails")
    public String getAccountDetails() {
        return "Account Controller";
    }
    
}
