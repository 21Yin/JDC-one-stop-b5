package com.jdc.token;

import com.jdc.token.exceptions.CustomAccessDeinedHandler;
import com.jdc.token.security.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfigurations {
	@Autowired
	private CustomAccessDeinedHandler customAccessDeinedHandler;

	@Autowired
	private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain http(HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable());
		http.cors(cors -> {});
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.exceptionHandling(handler -> {
				handler.accessDeniedHandler(customAccessDeinedHandler);
		});
		http.authorizeHttpRequests(reqest -> {
			reqest.requestMatchers("/public/**").permitAll();
			reqest.requestMatchers("/admin/**").hasAuthority("Admin");
			reqest.requestMatchers("/member/**").hasAuthority("Member");
			reqest.anyRequest().denyAll();
		});

		http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
