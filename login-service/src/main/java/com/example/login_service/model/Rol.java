package com.example.login_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO para la clase rol, representa el rol del usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Schema(description = "ID único del rol")
    private Long idRol;

    @Schema(description = "Nombre del rol")
    private String nombre;
}
