package com.example.restapitokensecurity.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class TokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public TokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader("Authorization");
        var authorization = tokenProvider.parse(token);


        if (null != authorization && authorization.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authorization);
        }
        filterChain.doFilter(request, response);
    }
}
