package com.edj.teamoop.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http:localhost:4200")
public class HelloController {
    @GetMapping("/hello")
    public String Hello() {
        return "Hello, les devs!";
    }

}
