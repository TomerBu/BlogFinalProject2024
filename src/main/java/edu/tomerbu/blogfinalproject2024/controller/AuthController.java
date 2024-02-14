package edu.tomerbu.blogfinalproject2024.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> userDetails(Authentication authentication) {
        return ResponseEntity.ok(
                Map.of(
                        "username", authentication.getName(),
                        "authorities",authentication.getAuthorities()
                )
        );
    }
}

//SortAlgorithm
//QuickSortImpl
//BubbleSortImpl