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

    public Usuario userExists(String email) {
        Usuario usuarioRequest = new Usuario();
        usuarioRequest.setEmail(email); // define la request que se va a enviar como pregunta a register/exists
        
        String url_register_service = "http://localhost:8082/api-v1/register/exists"; // url que devuelve un objeto Usuario
        
        try {
            Usuario usuarioResponse = restTemplate.postForObject(url_register_service, usuarioRequest, Usuario.class);
            if (usuarioResponse != null) {
                return usuarioResponse;
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al verificar existencia del usuario" + e.getMessage());
        }
    }

    public boolean validateUser(String email, String password) {
        Usuario usuario1 = userExists(email);
        if (usuario1 == null) {
            return false;

        }
        
        if (passwordEncoder.matches(password, usuario1.getPassword())) {
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
