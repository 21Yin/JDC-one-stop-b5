package com.example.restapitokensecurity.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.validation.constraints.NotBlank;

public record LoginForm(
        @NotBlank(message = "Enter Login id.")
        String username,
        @NotBlank(message = "Enter password.")
        String password
) {
    public Authentication authentication() {
        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }
}
