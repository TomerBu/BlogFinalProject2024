package edu.tomerbu.blogfinalproject2024.repository;

import edu.tomerbu.blogfinalproject2024.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByNameIgnoreCase(String roleName);
}
