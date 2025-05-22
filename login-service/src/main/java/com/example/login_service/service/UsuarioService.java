package com.example.login_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.login_service.model.Usuario;
import com.example.login_service.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    //TODO:BIEN
    public Usuario userExists(String email) {
        String url = "http://localhost:8082/api-v1/register/exists?email=" + email;
        try {
            Usuario exist = restTemplate.getForObject(url, Usuario.class);
            if (exist != null) {
                return exist;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar existencia del usuario", e);
        }
    }

    public boolean validateUser(String email, String password) {
        Usuario usuario11 = userExists(email);
        if (usuario11 == null) {
            return false;

        }
        
        if (passwordEncoder.matches(email, password)) {
        return true;
        
        }
        
        return false;
        }

    public Boolean existsByEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent(); 
    }

    public String encrypt(String rawPasswd) {
        return passwordEncoder.encode(rawPasswd);
    }

}
