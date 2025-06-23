package com.example.login_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.example.login_service.model.Usuario;
import com.example.login_service.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void userExists_returnsUserWhenExists() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        when(restTemplate.postForObject(anyString(), any(Usuario.class), eq(Usuario.class)))
            .thenReturn(usuario);

        Usuario result = usuarioService.userExists(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void validateUser_returnsTrueWhenCredentialsMatch() {
        String email = "email@ejemplo.com";
        String encPassword = "encoded12345";

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(encPassword);

        when(restTemplate.postForObject(anyString(), any(Usuario.class), eq(Usuario.class)))
            .thenReturn(usuario);

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean result = usuarioService.validateUser(email, encPassword);
        assertTrue(result);
    }

    @Test
    void existsByEmail_returnsTrueIfUserExists() {
        when(usuarioRepository.findByEmail("user@gmail.com"))
            .thenReturn(Optional.of(new Usuario()));

        boolean result = usuarioService.existsByEmail("user@gmail.com");
        assertTrue(result);
    }

    @Test
    void encrypt_returnsEncodedPassword(){
        String rawPassword = "1234";
        String encoded = "encodedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encoded);

        String result = usuarioService.encrypt(rawPassword);
        assertEquals(encoded, result);
    }
}
