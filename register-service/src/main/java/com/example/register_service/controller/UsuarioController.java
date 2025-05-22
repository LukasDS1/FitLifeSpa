package com.example.register_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.register_service.model.Usuario;
import com.example.register_service.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-v1/register")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crearUsuario")
    public ResponseEntity<?> CrearUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.existsByMail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }
        usuarioService.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
    }

    @GetMapping("/exists")
    public ResponseEntity<?> existsByMail(@RequestParam String email) {
        try {
            Boolean usuario1 = usuarioService.existsByMail(email);
            if (usuario1) {
                return ResponseEntity.ok(false);
            }
            return ResponseEntity.ok(usuario1);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar." + e.getMessage());
        }
    }

}
