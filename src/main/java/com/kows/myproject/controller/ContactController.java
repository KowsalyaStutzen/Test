package com.kows.myproject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ContactController {
 
    @GetMapping("contactDetails")
    public String getContactDetails() {
        return "Contact Controller";
    }
    
}
