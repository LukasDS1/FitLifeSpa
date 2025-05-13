package com.example.register_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.example.register_service.model.Usuario;
import com.example.register_service.service.UsuarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api-v1/register")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crearUsuario")
    public ResponseEntity<Usuario> CrearUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.UsuarioExistente(usuario.getEmail())) {
           return ResponseEntity.badRequest().build();
        }   
        usuarioService.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario creado correctamente.");
    }

}
