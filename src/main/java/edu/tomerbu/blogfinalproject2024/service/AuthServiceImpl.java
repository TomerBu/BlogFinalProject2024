package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.LoginRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.LoginResponseDTO;
import edu.tomerbu.blogfinalproject2024.dto.UserRequestDto;
import edu.tomerbu.blogfinalproject2024.dto.UserResponseDto;
import edu.tomerbu.blogfinalproject2024.entity.User;
import edu.tomerbu.blogfinalproject2024.error.AuthenticationException;
import edu.tomerbu.blogfinalproject2024.error.UserAlreadyExistsException;
import edu.tomerbu.blogfinalproject2024.repository.RoleRepository;
import edu.tomerbu.blogfinalproject2024.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    //props:
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;



    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        var user = getUserEntityOrThrow(dto.username());

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new AuthenticationException("Username or password don't match");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList()
        );

        var jwt = jwtService.jwtToken(authentication);
        return new LoginResponseDTO(jwt);
    }


    @Override
    public UserResponseDto register(UserRequestDto dto) {
        //check that the user does not exist email/username:
        userRepository.findUserByUsernameIgnoreCaseOrEmailIgnoreCase(dto.username(), dto.email()).ifPresent((u) -> {
            throw new UserAlreadyExistsException(u.getUsername(), u.getEmail());
        });

        var user = modelMapper.map(dto, User.class);
        //encrypt password
        user.setPassword(passwordEncoder.encode(dto.password()));

        var role = roleRepository.findRoleByNameIgnoreCase("ROLE_USER").orElseThrow();
        user.setRoles(Set.of(role));

        var saved = userRepository.save(user);

        return modelMapper.map(saved, UserResponseDto.class);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetch our entity from database:
        User user = getUserEntityOrThrow(username);

        //map the roles for Spring:
        //map our Set<Role> to Set<spring.Role>
        var roles =
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toSet());

        //map the user to Spring User:
        //return new Spring User:
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

    private User getUserEntityOrThrow(String username) {
        return userRepository.findUserByUsernameIgnoreCase(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
    }
}
