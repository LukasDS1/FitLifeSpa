package com.example.login_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.login_service.model.Usuario;
import com.example.login_service.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> validateUsuario(@RequestBody Usuario usuario) {
        try {
            if(usuarioService.validateUser(usuario.getEmail(), usuario.getPassword())){
             return ResponseEntity.accepted().body("Usuario Ingresado con éxito: "+usuario.getEmail()+" ¡Bienvenido a FitLife Spa!");
            }
            return ResponseEntity.badRequest().body("Error: Email o contraseña incorrectas");
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar." + e.getMessage());
        }
    }
    
    
}
