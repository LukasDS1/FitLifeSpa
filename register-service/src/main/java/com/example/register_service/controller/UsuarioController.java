package com.example.register_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.register_service.model.Usuario;
import com.example.register_service.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-v1/register")
@RequiredArgsConstructor
public class UsuarioController {
    
    
    private final UsuarioService usuarioService;


    @PostMapping("/crearUsuario")
    public ResponseEntity<?> CrearUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.getByMail(usuario.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }
        usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente.");
    }

    @PostMapping("/exists")
    public ResponseEntity<?> getUserByMail(@RequestBody Usuario usuario) {
        try {
            Usuario usuario1 = usuarioService.getByMail(usuario.getEmail());
            if (usuario1 != null) {
                return ResponseEntity.status(HttpStatus.OK).body(usuario1);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar." + e.getMessage());
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<?> existsById (@PathVariable Long id){
        Usuario usuario1 = usuarioService.buscarPorId(id);
        if (usuario1 == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario1);
    }

}
