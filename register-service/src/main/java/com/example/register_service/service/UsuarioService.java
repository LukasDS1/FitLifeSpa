package com.example.register_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.register_service.model.Usuario;
import com.example.register_service.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
   
    private final RestTemplate restTemplate;

    public String encrypt(String password) {
        return passwordEncoder.encode(password);

    }

    public Boolean UsuarioExistente(String email) {
        String url = "http://localhost:8081/api-v1/exists?email=" + email;
        try {
            Boolean exist = restTemplate.getForObject(url, boolean.class);
            if (exist == null || !exist) {
                return false;
            }
            return exist;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar existencia del usuario", e);
        }
    }
    

    public Usuario createUsuario(Usuario usuario) {
        if (UsuarioExistente(usuario.getEmail())) {
            throw new IllegalArgumentException("Usuario ya registrado!");
        }
        usuario.setPassword(encrypt(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

}
