package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.LoginRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.LoginResponseDTO;
import edu.tomerbu.blogfinalproject2024.dto.UserRequestDto;
import edu.tomerbu.blogfinalproject2024.dto.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    UserResponseDto register(UserRequestDto dto);

    LoginResponseDTO login(LoginRequestDTO dto);
}
