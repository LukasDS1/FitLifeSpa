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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

                Map<String, Object> response = new LinkedHashMap<>();

                response.put("self", "http://localhost:8083/api-v1/login");
                response.put("crear-formulario", "http://localhost:8092/api-v1/form/post");
                response.put("crear-servicio", "http://localhost:8085/api-v1/service/add");
                response.put("crear-clase", "http://localhost:8088/api-v1/clase/crear");
                response.put("crear-inscripcion", "http://localhost:8089/api-v1/inscripciones");
                response.put("crear-membresia", "http://localhost:8086/api-v1/membresia/crearmembresia");
                response.put("crear-reserva", "http://localhost:8087/api-v1/reservas/add");
                return ResponseEntity.accepted().body(response);

            }
            return ResponseEntity.badRequest().body("Error: Email o contraseña incorrectas");
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar." + e.getMessage());
        }
    }
    
    
}
