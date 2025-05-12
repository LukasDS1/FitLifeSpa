package com.example.login_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login_service.model.Usuario;
import com.example.login_service.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/exists")
    public ResponseEntity<?> existsByEmail(@RequestParam String email) {
        try {
            Boolean exists = usuarioService.existsByEmail(email);
            if (!exists) {
                return ResponseEntity.ok(false);
            }
            return ResponseEntity.ok(exists);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar." + e.getMessage());
        }
        

    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> validateUsuario(@RequestBody Usuario usuario) {
        try {
            return ResponseEntity.accepted().body(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar." + e.getMessage());
        }
    }
    
    
}
