package com.example.login_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.login_service.model.Usuario;
import com.example.login_service.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Este endpoint permite validar usuario al iniciar sesión")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "202", 
            description = "Usuario validado correctamente",
            content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Usuario ingresado con éxito: usuario@gmail.com"))),

        @ApiResponse(
            responseCode = "400", 
            description = "Email o contraseña incorrectos")
    })
    @PostMapping("/login")
    public ResponseEntity<?> validateUsuario(@RequestBody Usuario usuario) {
        try {
            if(usuarioService.validateUser(usuario.getEmail(), usuario.getPassword())){
            return ResponseEntity.accepted().body("Usuario ingresado con éxito: "+ usuario.getEmail()+ " ¡Bienvenido a FitLife Spa!");

            }
            return ResponseEntity.badRequest().body("Error: Email o contraseña incorrectas");
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar." + e.getMessage());
        }
    }
    
    
}
