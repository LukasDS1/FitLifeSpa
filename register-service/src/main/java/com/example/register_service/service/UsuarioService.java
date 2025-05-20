package com.example.register_service.service;

import java.util.Optional;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.register_service.model.Usuario;
import com.example.register_service.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public Boolean existsByEmail(String email) {
       Optional<Usuario> existe = usuarioRepository.findByEmail(email);
        if(existe.isPresent()) {
            return true;
        }
        return false;

    }


    public Usuario createUsuario(Usuario usuario){
        Usuario usuario1 = new Usuario();
        usuario1.setEmail(usuario1.getEmail());
        usuario1.setNombre(usuario1.getNombre());
        usuario1.setApellidoPaterno(usuario1.getApellidoPaterno());
        usuario1.setApellidoMaterno(usuario1.getApellidoMaterno());
        usuario1.setGenero(usuario1.getGenero());
        usuario1.setRut(usuario1.getRut());
         usuario1.getRol();
        usuario1.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario1);
    
    }

    
    

   

}