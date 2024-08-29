package com.chat.application.security.config;


import com.chat.application.security.PasswordEncoder;
import com.chat.application.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf( csrf -> csrf.disable())
                .authorizeHttpRequests( authentication -> authentication
                        .requestMatchers("/api/v1/registration/**", "/css/**", "/images/**").permitAll()
                        .requestMatchers("/registration").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/", "/chat").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/chat", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .authenticationProvider(authenticationProvider())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return httpSecurity.build();
    }



    @Bean
    DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder.encode());
            provider.setUserDetailsService(userService);

        return provider;
    }
}
