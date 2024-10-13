package com.chat.application.security.config;


import com.chat.application.security.PasswordEncoder;
import com.chat.application.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf( csrf -> csrf.disable())
                .authorizeHttpRequests( authentication -> authentication
                        .requestMatchers("/api/v1/create-user").permitAll()
                        .requestMatchers("/api/v1/login-user").permitAll()
                        .requestMatchers("/api/v1/search-user").permitAll()
                        .requestMatchers("/chat/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
