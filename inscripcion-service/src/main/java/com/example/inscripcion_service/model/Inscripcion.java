package com.example.inscripcion_service.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inscripcion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "La inscripcion que hace el usuario")
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id unico para la inscripcion")
    private Long idInscripcion;
    
    @Column(nullable = false)
    @Schema(description = "La fecha en la que se hizo la inscripcion")
    private Date fechaInscripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idClase")
    @JsonIgnoreProperties("inscripcion")
    @Schema(description = "Las clases en las que se inscribio")
    private Clase clase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEstado")
    @JsonIgnoreProperties("inscripcion")
    @Schema(description = "El estado de la inscripcion")
    private Estado estado;

    @Column(nullable = false)
    @Schema(description = "el usuario al que pertenece la inscripcion")
    private Long idUsuario;

}
