package com.example.login_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

    // Manejar error al conseguir usuario por email, para evitar excepciones (error 500)
    // Conseguir objeto de usuario conectandose al microservicio de registro.
    // Esta forma sólo funciona si se tiene el objeto que se va a conseguir.
    public Usuario userExists(String email) {
        Usuario usuarioRequest = new Usuario(); // Crea la request donde se guarda un objeto Usuario.
        usuarioRequest.setEmail(email); // agrega la información que se enviará para la request (email)
        
        String url_register_service = "http://localhost:8082/api-v1/register/exists"; // url que devuelve un objeto Usuario
        
        try { // define los parámetros necesarios para el restTemplate (url, request con la info, respuesta de register)
            Usuario usuarioResponse = restTemplate.postForObject(url_register_service, usuarioRequest, Usuario.class);
            return usuarioResponse; // Devuelve el objeto de Usuario para ser utilizado después.
        } catch (HttpClientErrorException e) { // prevee errores del lado de register (al no encontrar al usuario)
            return null;

        } catch (Exception e) { // prevee cualquier otro error.
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
