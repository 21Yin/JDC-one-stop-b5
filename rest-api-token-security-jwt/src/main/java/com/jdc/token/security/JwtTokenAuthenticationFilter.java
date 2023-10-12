package com.jdc.token.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
public class JwtTokenAuthenticationFilter  extends OncePerRequestFilter {
    @Autowired
    private  JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var authentication = jwtTokenProvider.parse(request.getHeader("Authentication"));


        if(null != authentication && authentication.isAuthenticated()){
            SecurityContextHolder.getContext();
        }
        filterChain.doFilter(request,response);
    }
}
