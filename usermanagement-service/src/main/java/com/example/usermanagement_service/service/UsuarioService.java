package com.example.usermanagement_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.usermanagement_service.model.Usuario;
import com.example.usermanagement_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;

    private final RestTemplate restTemplate;

    public String encrypt(String password){
        return passwordEncoder.encode(password);
    }


    public Usuario saveUser(Usuario usuario){

        Usuario usuario1 = usuarioRepository.findByEmail(usuario.getEmail()).orElseThrow(() -> new RuntimeException("Usuario inexistente"));
        usuario1.setPassword(usuario.getPassword());
        usuario1.setNombre(usuario.getNombre());
        usuario1.setApellidoPaterno(usuario.getApellidoPaterno());
        usuario1.setApellidoMaterno(usuario.getApellidoMaterno());
        usuario1.setGenero(usuario.getGenero());
        usuario1.setRut(usuario.getRut());
        usuario1.setPassword(encrypt(usuario.getPassword()));

        return usuarioRepository.save(usuario1);
    }

        public Usuario exist(String email) {
        Usuario usuarioRequest = new Usuario();
        usuarioRequest.setEmail(email);

        String url_register_service = "http://localhost:8082/api-v1/register/exists";

        try {
            Usuario Usuarioexist = restTemplate.postForObject(url_register_service, usuarioRequest,Usuario.class);
              System.out.println("Usuario encontrado: " + Usuarioexist);
            return Usuarioexist;
        } catch (HttpClientErrorException e) {
        System.out.println("Error HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        return null;
        } catch (Exception e){
        throw new RuntimeException("Error al verificar existencia del usuario"+ e.getMessage());
    }
    }

    public boolean validateUser(String email){
        if(email == null || email.trim().isEmpty()){
            return false;
        }
        Usuario usuario1 = exist(email);
        return usuario1 != null;
    }


    public Boolean deleteUser(String email){
            Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Usuario de email: "+email +" No encontrado!"));
            usuarioRepository.delete(usuario);
            return true;
        }

}
