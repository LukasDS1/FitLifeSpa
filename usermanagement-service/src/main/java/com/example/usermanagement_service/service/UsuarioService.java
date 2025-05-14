package com.example.usermanagement_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

   public Boolean Exist(String email) {
        String url = "http://localhost:8081/api-v1/exists?email="+email;
        try {
            Boolean exist = restTemplate.getForObject(url, boolean.class);
            if (exist != null) {
                return exist;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar existencia del usuario", e);
        }
    }


    public Boolean deleteUser(String email){
            Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Usuario de email: "+email +" No encontrado!"));
            usuarioRepository.delete(usuario);
            return true;
        }

}
