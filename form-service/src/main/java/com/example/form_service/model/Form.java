package com.example.form_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Entidad que representa el formulario contacto del usuario")
@Entity
@Table(name = "form")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form {

    @Schema(description = "ID único del formulario")
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long idFormulario;

    @Schema(description = "Nombre del usuario que envía el formulario")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Schema(description = "Apellidos del usuario que envía el formulario")
    @Column(nullable = false, length = 100)
    private String apellidos;

    @Schema(description = "Correo de contacto del usuario")
    @Column(nullable = false, length = 255)
    private String correo;

    @Schema(description = "Titulo del mensaje enviado")
    @Column(nullable = false, length = 30)
    private String titulo;

    @Schema(description = "Mensaje del formulario enviado")
    @Column(nullable = false)
    private String mensaje;
}
