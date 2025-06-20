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
@Table(name = "estado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "El estado en el que se encuentra")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id unico para cada estado")
    private Long idEstado;

    @Column(nullable = false)
    @Schema(description = "Nombre del estado")
    private String nombre;
    
    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "cuales inscripciones tienen este estado")
    List<Inscripcion> inscripciones;
}
