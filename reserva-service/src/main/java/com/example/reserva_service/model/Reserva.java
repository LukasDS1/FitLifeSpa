package com.example.reserva_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "reservas")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Las reservas para cada servicio")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id unico para cada reserva")
    private Long idReserva;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    @Schema(description = "Fecha para la reserva")
    private Date fechaReserva;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    @Schema(description = "Fecha en la que se firmo el contrato para la reserva")
    private Date fechaContrato;
    
    @Column(nullable = false)
    @Schema(description = "Descripcion detallando la reserva")
    private String descripcion;
    
    @Column(nullable = true)
    @Schema(description = "El entrenador encargado para el servicio")
    private Long idEntrenador;

    @Column(nullable = false)
    @Schema(description = "El usuario que hizo la reserva de un servicio")
    private Long idUsuario;

    @Column(nullable = false)
    @Schema(description = "El servicio reservado")
    private Long idServicio;
    
    @OneToMany(mappedBy = "reserva" , cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "El estado de la reserva")
    List<EstadoReserva> estadoReservas;
}
