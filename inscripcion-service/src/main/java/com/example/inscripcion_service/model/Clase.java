package com.example.inscripcion_service.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "clase")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Las clases de fitlifespa")
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico para cada clase")
    private Long idClase;

    @Column(nullable = false)
    @Schema(description = "El nombre de la clase")
    private String nombre;
    
    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Las inscripciones ligadas a la clase")
    List<Inscripcion> inscripciones;
}
