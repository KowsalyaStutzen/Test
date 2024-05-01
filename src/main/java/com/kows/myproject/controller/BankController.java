package com.kows.myproject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class BankController {
 
    @GetMapping("bank")
    public String getBankDetails() {
        return "Bank Controller";
    }
    
}
