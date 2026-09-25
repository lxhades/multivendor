package com.auth_service.application.port.in;

import com.auth_service.domain.model.enumtype.UserStatus;

import java.util.UUID;

public record RegisterUserResult(
        UUID userId,
        String email,
        String phone,
        UserStatus status
) {
}

