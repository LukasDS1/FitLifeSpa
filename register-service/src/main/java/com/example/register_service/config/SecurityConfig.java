package com.example.register_service.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            //.requestMatchers(HttpMethod.POST,"/api-v1/register/**").permitAll()
            //.requestMatchers(HttpMethod.GET,"/api-v1/register/**").permitAll()
            .anyRequest().permitAll()
            )
            .httpBasic(withDefaults());
            return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // TODO: Mover este código a login.

    // private final UsuarioRepository usuarioRepository;
    // @Bean
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    //     return config.getAuthenticationManager();
    // }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    //     authenticationProvider.setUserDetailsService(usuarioDetailService());
    //     authenticationProvider.setPasswordEncoder(passwordEncoder());
    //     return authenticationProvider;
    // }

    // @Bean
    // public UserDetailsService usuarioDetailService() {
    //     return username -> usuarioRepository.findByEmail(username)
    //     .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));
    // }


}
