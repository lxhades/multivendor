package com.auth_service.domain.model.vo;

public record Phone(String value) {

    public Phone {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Phone must not be blank");
        }
    }

}