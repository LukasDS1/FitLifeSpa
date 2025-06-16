package com.example.register_service.controller;


import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.register_service.dto.RegisterRequestDto;
import com.example.register_service.model.AuthResponse;
import com.example.register_service.model.Usuario;
import com.example.register_service.service.AuthService;
import com.example.register_service.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api-v1/auth")
@RequiredArgsConstructor
public class UsuarioController {
    
    
    private final UsuarioService usuarioService;
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
        // if (usuarioService.getByMail(usuario.getEmail()) != null) {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        // }
        // usuarioService.saveUsuario(usuario);
        // return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente.");
    }

    // @PostMapping("/exists")
    // public ResponseEntity<?> getUserByMail(@RequestBody Usuario usuario) {
    //     try {
    //         Usuario usuario1 = usuarioService.getByMail(usuario.getEmail());
    //         if (usuario1 != null) {
    //             return ResponseEntity.status(HttpStatus.OK).body(usuario1);
    //         }
    //         return ResponseEntity.notFound().build();
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error al verificar." + e.getMessage());
    //     }
    // }

    // @GetMapping("/exists/{id}")
    // public ResponseEntity<?> existsById (@PathVariable Long id){
    //     Usuario usuario1 = usuarioService.buscarPorId(id);
    //     if (usuario1 == null){
    //         return ResponseEntity.notFound().build();
    //     }
    //     return ResponseEntity.ok(usuario1);
    // }

}
