package com.kows.myproject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class NoticesController {
 
    @GetMapping("notices")
    public String getNoticeDetails() {
        return "Notice Controller";
    }
    
}
