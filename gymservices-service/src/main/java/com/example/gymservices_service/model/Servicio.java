package com.example.gymservices_service.model;

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

@Schema(description = "Entidad que representa un servicio ofrecido por FitLife Spa.")
@Entity
@Table(name = "servicio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    
    @Schema(description = "ID único del servicio")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Schema(description = "Nombre del servicio")
    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @Schema(description = "Descripción breve del servicio")
    @Column(nullable = false, length = 200)
    private String descripcion;
}
