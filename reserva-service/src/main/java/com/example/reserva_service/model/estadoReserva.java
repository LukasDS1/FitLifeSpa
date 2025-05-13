package com.example.reserva_service.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "estadoReserva")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class estadoReserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoReserva;

    @Column(nullable = false)
    private Date fechaEstadoReserva;

    @Column(nullable = false)
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idReserva")
    @JsonIgnoreProperties("EstadoReservas")
    private Reserva reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEstado")
    @JsonIgnoreProperties("EstadosReservas")
    private Estado estado;
}
