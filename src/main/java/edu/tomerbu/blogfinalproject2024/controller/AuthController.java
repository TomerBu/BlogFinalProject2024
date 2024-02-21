package edu.tomerbu.blogfinalproject2024.controller;


import edu.tomerbu.blogfinalproject2024.dto.LoginRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.LoginResponseDTO;
import edu.tomerbu.blogfinalproject2024.dto.UserRequestDto;
import edu.tomerbu.blogfinalproject2024.dto.UserResponseDto;
import edu.tomerbu.blogfinalproject2024.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        var resDto = authService.login(dto);
        return ResponseEntity.ok(resDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto dto, UriComponentsBuilder uriBuilder) {
        return ResponseEntity.created(uriBuilder.path("/api/v1/auth/login").build().toUri()).body(authService.register(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> userDetails(Authentication authentication) {
        return ResponseEntity.ok(
                Map.of(
                        "username", authentication.getName(),
                        "authorities", authentication.getAuthorities()
                )
        );
    }
}

//SortAlgorithm
//QuickSortImpl
//BubbleSortImpl