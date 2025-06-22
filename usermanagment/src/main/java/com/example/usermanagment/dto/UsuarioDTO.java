package com.example.usermanagment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Schema(description = " DTO de la clase usuario la cual se encarga de almacenar los datos de la clase usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioDTO {
    @Schema(description = "ID unico del usuario")
    private Long idUsuario;

    @Schema(description = "Email del usuario")
    private String email;

    @Schema(description = "Contraseña del usuario")
    private String password;   

    @Schema(description = "Nombre del usuario") 
    private  String nombre;

    @Schema(description = "Apellido paterno del usuario")
    private String apellidoPaterno;

    @Schema(description = "Apellido materno del usuario")
    private String apellidoMaterno;
    
    @Schema(description = "Genero del usuario")
    private String genero;
    
    @Schema(description = "Rut del usuario")
    private String rut;

}
