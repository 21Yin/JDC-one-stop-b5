package com.example.restapitokensecurity.model;

public record LoginResult(
        boolean success,
        String message
) {
}
