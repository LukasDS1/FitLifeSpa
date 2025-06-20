package com.example.resena_service.model;

import java.util.Date;
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
import lombok.NoArgsConstructor;

@Table(name = "reseña")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Las resenias hechas para los servicios")
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id unica para la resenia")
    private Long idResenia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column
    @Schema(description = "El dia en el que se ejecuto la resenia")
    private Date fechaReseña;

    @Column(nullable = false)
    @Schema(description = "El contenido de la resenia")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "El usuario que hizo la reseña a partir de su ID")
    private Long idUsuario;

    @Column(nullable = false)
    @Schema(description = "Refleja a que servicio pertenece la resenia mediante su ID")
    private Long idServicio;
    
}
