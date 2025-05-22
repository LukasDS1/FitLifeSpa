package com.example.gymservices_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servicio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;
}
