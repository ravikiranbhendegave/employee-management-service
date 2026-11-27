package com.ravicode.employee.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
