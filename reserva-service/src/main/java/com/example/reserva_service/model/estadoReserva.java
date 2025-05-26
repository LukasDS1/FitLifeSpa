package com.example.reserva_service.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "estadoReserva")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class estadoReserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoReserva;

    @Column(nullable = false)
    private Date fechaEstadoReserva;

    @Column(nullable = false)
    private String comentario;

    @Column
    private Long idEstado;

    @ManyToOne
    @JoinColumn(name = "idReserva")
    @JsonIgnoreProperties("estadoReserva")
    private Reserva reserva;
}
