package com.kows.myproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
    
    @GetMapping("cardDetails")
    public String getCardDetails() {
        return "Card Controller";
    }
}
