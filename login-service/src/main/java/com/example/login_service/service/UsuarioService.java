package com.example.login_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public boolean validateUsuario(String email, String password) {
    if (!existsByEmail(email)) { 
        return false; 
    }

    Usuario usuario = usuarioRepository.findByEmail(email).get();
    if (passwordEncoder.matches(password, usuario.getPassword())) {
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
