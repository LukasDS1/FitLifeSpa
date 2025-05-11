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
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/api-v1/usuario")
public class UsuarioController {

    @Autowired 
    private UsuarioService usuarioService;

    private RestTemplate restTemplate;

    @PostMapping("/crearUsuario")
    public ResponseEntity<Usuario> CrearUsuario (@RequestBody Usuario usuario) {
            if(){
                 ResponseEntity.badRequest().build();   
            }
             usuarioService.createUsuario(usuario);
             return ResponseEntity.status(HttpStatus.ACCEPTED).build();
             
    }

    
    


}
