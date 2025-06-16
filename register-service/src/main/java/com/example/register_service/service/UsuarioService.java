package com.example.register_service.service;





import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.register_service.model.Usuario;
import com.example.register_service.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService {
// Esta clase se encarga de la gestión de usuarios "CRUD".
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public Usuario getByMail(String email) {
       Usuario usuario = usuarioRepository.findByEmail(email);
        if(usuario != null) {
            return usuario;
        }
        return null;
    }
    

    public Usuario saveUsuario(Usuario usuario){
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Usuario ya existente.");
        }

        Usuario usuario1 = new Usuario();
        usuario1.setEmail(usuario.getEmail());
        usuario1.setNombre(usuario.getNombre());
        usuario1.setApellidoPaterno(usuario.getApellidoPaterno());
        usuario1.setApellidoMaterno(usuario.getApellidoMaterno());
        usuario1.setGenero(usuario.getGenero());
        usuario1.setRut(usuario.getRut());
        usuario1.setRol(usuario.getRol());
        usuario1.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario1);
    }

    public Usuario findByID(Long id){
        return usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }


     public Usuario UpdateUserById(Usuario usuario){
          
        Usuario usuario1 = usuarioRepository.findById(usuario.getIdUsuario()).orElseThrow(() -> new EntityNotFoundException("Usuario inexistente"));
        if(usuario.getEmail() != null && !usuario.getEmail().trim().isEmpty()){
            usuario1.setEmail(usuario.getEmail());
        }

        if(usuario.getNombre() != null && !usuario.getNombre().trim().isEmpty()){
            usuario1.setNombre(usuario.getNombre());
        }

        if(usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().trim().isEmpty()){
            usuario1.setApellidoPaterno(usuario.getApellidoPaterno());
        }

        if(usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().trim().isEmpty()){
            usuario1.setApellidoMaterno(usuario.getApellidoMaterno());
        }

        if(usuario.getGenero() != null && !usuario.getGenero().trim().isEmpty()){
            usuario1.setGenero(usuario.getGenero());
        }

        if(usuario.getRut() != null && !usuario.getRut().trim().isEmpty()){
            usuario1.setRut(usuario.getRut());
        }
        
        if(usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()){
            usuario1.setPassword(encrypt(usuario.getPassword()));
        }

        if(usuario.getRol() != null ) {
            usuario1.setRol(usuario.getRol());
        }

        return usuarioRepository.save(usuario1);

    }


   
   
}




    
    

   

