package com.example.restapitokensecurity.config;

import com.example.restapitokensecurity.service.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final TokenFilter tokenFilter;
    private final ApplicationAccessDeinedHandler applicationAccessDeinedHandler;

    public SecurityConfiguration(TokenFilter tokenFilter, ApplicationAccessDeinedHandler applicationAccessDeinedHandler) {
        this.tokenFilter = tokenFilter;
        this.applicationAccessDeinedHandler = applicationAccessDeinedHandler;
    }

    @Bean
    SecurityFilterChain http(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> {});

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(req -> {
            req.requestMatchers("/public/**").permitAll();
            req.requestMatchers("/admin/**").hasAuthority("Admin");
            req.requestMatchers("/member/**").hasAuthority("Member");
            req.anyRequest().denyAll();
        });

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(exception -> {
            exception.accessDeniedHandler(null);
        });
        return http.build();
    }




    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



//    @Bean
    private UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin@gmail.com").authorities("Admin", "Read", "Write").password("{noop}admin").build(),
                User.withUsername("member@gmail.com").authorities("Member", "Read").password("{noop}member").build()
        );

    }
}
