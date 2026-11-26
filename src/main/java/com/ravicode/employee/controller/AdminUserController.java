package com.ravicode.employee.controller;

import com.ravicode.employee.dto.CreateUserRequest;
import com.ravicode.employee.model.AppUser;
import com.ravicode.employee.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@Valid @RequestBody CreateUserRequest request) {
        if (appUserRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username already exists");
        }

        AppUser user = new AppUser(
                request.username(),
                passwordEncoder.encode(request.password()),
                "USER");
        appUserRepository.save(user);

        return "User created successfully";
    }
}
