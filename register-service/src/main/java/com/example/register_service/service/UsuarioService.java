package com.example.register_service.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.register_service.model.Usuario;
import com.example.register_service.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private RestTemplate restTemplate;
    
    public String encrypt(String password){
        return passwordEncoder.encode(password);

    }

    public boolean UsuarioExistente(String Email){
        String url = "";
        return restTemplate.getForObject(url,boolean.class);
    }

    public Usuario createUsuario(String email,Usuario usuario){
        if(UsuarioExistente(email)){
            throw new IllegalArgumentException("Usuario ya registrado!");
        }
        usuario.setPassword(encrypt(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

     




    









   



}
