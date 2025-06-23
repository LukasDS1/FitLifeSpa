package com.example.login_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO para clase usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Schema(description = "ID único del usuario")
    private Long idUsuario;

    @Schema(description = "Email del usuario")
    private String email;

    @Schema(description = "Contraseña del usuario")
    private String password;

    @Schema(description = "Nombre del usuario") 
    private String nombre;

    @Schema(description = "Apellido paterno del usuario")
    private String apellidoPaterno;

    @Schema(description = "Apellido materno del usuario")
    private String apellidoMaterno;

    @Schema(description = "Genero del usuario")
    private String genero;

    @Schema(description = "Rut del usuario")
    private String rut;

    @Schema(description = "ID del rol asignado al usuario")
    private Rol rol;
}