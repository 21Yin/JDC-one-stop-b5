package com.example.restapitokensecurity.api.exceptions;

import com.example.restapitokensecurity.model.ErrorResponse;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    private final Map<Class<?>, String> authenticationErrors = Map.of(
            UsernameNotFoundException.class, "Please check your login id.",
            BadCredentialsException.class, "Please Check your password"
    );

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse handle(AccessDeniedException e){
        return new ErrorResponse(ErrorResponse.Type.Authorization,
                List.of("You have no permission for this operation."));
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse handle(ArithmeticException e){
        var message = authenticationErrors.get(e.getClass());
        return new ErrorResponse(ErrorResponse.Type.Authentication,
                List.of( null != message ? message: e.getMessage()));

    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handle(ValidationFailureException e){
        return new ErrorResponse(ErrorResponse.Type.Validation, List.of(e.getMessage()));
    }
}
