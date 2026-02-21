package com.example.login_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Define URL authorization rules.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/error", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Enable OAuth2 login and configure custom pages/redirects.
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/success", true)
                )
                // Enable logout endpoint and redirect after logout.
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                // Keep defaults for CSRF/session handling (recommended for Spring Security 6+).
                .csrf(Customizer.withDefaults());

        return http.build();
    }
}
