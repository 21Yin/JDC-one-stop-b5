package com.example.restapitokensecurity.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class Admin {
    @GetMapping
    String index(){
        return "Hello From Admin Api";
    }
}
