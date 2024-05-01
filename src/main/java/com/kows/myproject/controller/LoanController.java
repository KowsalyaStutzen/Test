package com.kows.myproject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class LoanController {
 
    @GetMapping("loanDetails")
    public String getLoanDetails() {
        return "Loan Controller";
    }
    
}
