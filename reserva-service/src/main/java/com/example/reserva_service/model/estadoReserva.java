package com.example.reserva_service.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "El estado de la reserva, detalla la informacion de la reserva")
public class EstadoReserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id unico del estado de la reserva")
    private Long idEstadoReserva;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    @Schema(description = "La fecha reservada")
    private Date fechaEstadoReserva;

    @Column(nullable = false)
    @Schema(description = "Comentario adicional")
    private String comentario;

    @Column
    @Schema(description = "El estado en el que se encuentra la reserva")
    private Long idEstado;

    @ManyToOne
    @JoinColumn(name = "idReserva")
    @JsonIgnoreProperties("estadoReserva")
    @Schema(description = "Relacion con reserva")
    private Reserva reserva;
}
