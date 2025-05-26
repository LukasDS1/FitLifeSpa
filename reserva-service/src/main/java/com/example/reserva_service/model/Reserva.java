package com.example.reserva_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "reservas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @Column(nullable = false)
    private Date fechaReserva;
    
    @Column(nullable = false)
    private Date fechaContrato;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = true)
    private Long idEntrenador;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private Long idServicio;
    
    @OneToMany(mappedBy = "reserva" , cascade = CascadeType.ALL)
    @JsonIgnore
    List<estadoReserva> estadoReservas;
}
