package com.example.restapitokensecurity.api;

import com.example.restapitokensecurity.model.LoginForm;
import com.example.restapitokensecurity.model.LoginResult;
import com.example.restapitokensecurity.service.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("public")
public class Public {



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("login")
    LoginResult login(
            @Validated LoginForm form, BindingResult result)
    {
      //  var authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        var authentication = authenticationManager.authenticate(form.authentication());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = tokenProvider.generate(authentication);
        if (null != token){
            return new LoginResult(true, token);
        }
        return new LoginResult(false, "admin");

    }
}
