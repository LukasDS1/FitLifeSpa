package com.example.usermanagment.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(description = " DTO de la clase rol la cual se encarga de mapear la clase rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RolDTO extends RepresentationModel<RolDTO>{
    @Schema(description = " ID unico del Rol")
    private Long idRol;

    @Schema(description = " Nombre del Rol")
    private String nombre;

}
