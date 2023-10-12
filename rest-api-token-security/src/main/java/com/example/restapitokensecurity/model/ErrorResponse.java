package com.example.restapitokensecurity.model;

import java.util.List;

public record ErrorResponse(
        Type type,
        List<String> messages
) {

    public enum Type{
        Authentication,
        Authorization, Validation, Business, Platform
    }
}
