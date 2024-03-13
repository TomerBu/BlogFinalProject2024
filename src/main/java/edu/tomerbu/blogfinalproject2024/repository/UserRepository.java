package edu.tomerbu.blogfinalproject2024.repository;

import edu.tomerbu.blogfinalproject2024.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);

    Optional<User> findUserByUsernameIgnoreCase(String username);

    Optional<User> findUserByEmailIgnoreCase(String email);

    boolean existsUserByUsernameIgnoreCase(String username);
    boolean existsUserByEmailIgnoreCase(String email);
}
