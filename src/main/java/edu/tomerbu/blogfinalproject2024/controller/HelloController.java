package edu.tomerbu.blogfinalproject2024.controller;


import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hello")
@Hidden
public class HelloController {

    @GetMapping
    public Map<String, String> sayHello() {
        return Map.of("hi", "world");
    }
}
