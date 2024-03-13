package edu.tomerbu.blogfinalproject2024.config;


import edu.tomerbu.blogfinalproject2024.entity.Role;
import edu.tomerbu.blogfinalproject2024.entity.User;
import edu.tomerbu.blogfinalproject2024.repository.RoleRepository;
import edu.tomerbu.blogfinalproject2024.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SQLRunner implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    //אם היתה שגיאה בהכנסת אלמנט בודד -
    // יבצע roll back יבטל את כל ההכנסות באותו רגע

    // מאחורי הקלעים - יוצר proxy של ספרינג
    // קוד אלטרנטיבי לקוד שאנחנו כתבנו
    // עם קוד שמטפל במימוש של הtransaction
    // בודק אם היתה שגיאה - ומבצע rollback ליתר אם היתה שגיאה
    // Any RuntimeException or Error triggers rollback, and any checked Exception does not.
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            var adminRole = roleRepository.save(new Role(1L, "ROLE_ADMIN"));
            var userRole = roleRepository.save(new Role(2L, "ROLE_USER"));

            userRepository.save(
                    new User(
                            1L,
                            "admin",
                            "tomerbu@gmail.com",
                            passwordEncoder.encode("Passw0rd1!"),
                            Set.of(adminRole),
                            Set.of()
                    )
            );

            userRepository.save(
                    new User(
                            2L,
                            "user",
                            "moe@gmail.com",
                            passwordEncoder.encode("Passw0rd1!"),
                            Set.of(userRole),
                            Set.of()
                    )
            );
        }
    }
}
