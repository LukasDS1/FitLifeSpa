package com.example.inscripcion_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(description = "La fecha en la que se hizo la inscripcion")
    private Date fechaInscripcion;

    
    @Schema(description = "Coleccion de usuarios los cuales estan inscritos a una clase ")
    @ElementCollection
    @CollectionTable(name = "inscripcion_usuarios", joinColumns = @JoinColumn(name = "id_inscripcion"))
    @Column(name = "id_usuario")
    private List<Long> idUsuario;

    @Column(nullable = false)
    @Schema(description = "Estado de la inscripcion")
    private Long idEstado;
    
    @Column(nullable = false)
    @Schema(description = "Clase relacionada con la inscripcion")
    private Long idClase;

    

}
