package com.ravicode.employee.config;

import com.ravicode.employee.model.AppUser;
import com.ravicode.employee.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserInitializer {

    @Bean
    CommandLineRunner initializeAdminUser(
            AppUserRepository repository,
            PasswordEncoder passwordEncoder,
            @Value("${app.bootstrap-admin.username:}") String username,
            @Value("${app.bootstrap-admin.password:}") String password) {

        return args -> {
            // Bootstrap is disabled unless both values are configured.
            if (username.isBlank() && password.isBlank()) {
                return;
            }
            if (username.isBlank() || password.isBlank() || password.length() < 12) {
                throw new IllegalStateException(
                        "Configure ADMIN_USERNAME and ADMIN_PASSWORD; password must be at least 12 characters");
            }

            if (!repository.existsByUsername(username)) {
                repository.save(new AppUser(
                        username,
                        passwordEncoder.encode(password),
                        "ADMIN"));
            }
        };
    }
}
