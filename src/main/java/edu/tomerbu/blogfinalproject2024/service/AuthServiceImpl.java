package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    //props:
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetch our entity from database:
        var user = userRepository.findUserByUsernameIgnoreCase(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        //map the roles for Spring:
        //map our Set<Role> to Set<spring.Role>
        var roles =
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toSet());

        //map the user to Spring User:
        //return new Spring User:
        return new User(user.getUsername(), user.getPassword(), roles);
    }
}
