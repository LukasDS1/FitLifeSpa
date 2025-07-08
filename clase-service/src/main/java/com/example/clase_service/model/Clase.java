package com.example.clase_service.model;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(description = "Representa una clase agendada que ofrece el gimnasio")
@Entity
@Table(name = "clase")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Clase extends RepresentationModel<Clase> {

    @Schema(description = "Identificador único de la clase")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClase;

    @Schema(description = "Nombre de la clase")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Schema(description = "Fecha en la que la clase se realizará")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private Date fechaClase;

    @Schema(description = "Descripción breve de la clase")
    @Column(nullable = false)
    private String descripcion;

    @Schema(description = "ID del servicio asociado a la clase")
    @Column(nullable = false)
    private Long idServicio;
}
