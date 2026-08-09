package com.example.store.security;

import com.example.store.entity.User;
import com.example.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {
    @Bean
    CommandLineRunner seedAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.email}") String email,
            @Value("${app.admin.password}") String password) {

        return args -> {
            if (email.isBlank() || password.isBlank()) {
                return;
            }

            if (!userRepository.existsByEmail(email)) {
                User admin = User.createAdmin(
                        email,
                        passwordEncoder.encode(password),
                        "Admin",
                        "Admin"
                );

                userRepository.save(admin);
            }
        };
    }
}
