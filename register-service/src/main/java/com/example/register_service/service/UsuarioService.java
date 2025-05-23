package com.example.register_service.service;



import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    
    

   

}