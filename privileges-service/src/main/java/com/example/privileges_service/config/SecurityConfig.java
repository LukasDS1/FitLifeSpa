package com.example.privileges_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http // Cross-Site Request Forgery
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                //.requestMatchers(HttpMethod.GET,"/api-v1/**").permitAll()
                //.requestMatchers(HttpMethod.POST,"/api-v1/**").permitAll()
                .anyRequest().permitAll()
            )
            .httpBasic(withDefaults());

        return http.build();
    }
}