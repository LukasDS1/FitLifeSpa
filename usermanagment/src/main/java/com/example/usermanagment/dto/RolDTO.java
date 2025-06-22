package com.example.usermanagment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = " DTO de la clase rol la cual se encarga de mapear la clase rol")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RolDTO {
    @Schema(description = " ID unico del Rol")
    private Long idRol;

    @Schema(description = " Nombre del Rol")
    private String nombre;

}
