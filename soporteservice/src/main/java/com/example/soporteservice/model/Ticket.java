package com.example.soporteservice.model;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Schema(description = "Este es el modelo para Ticket")
@Entity
@Table(name="ticket")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Ticket extends RepresentationModel<Ticket>{
    @Schema(description = "ID unico para Ticket")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @Schema(description = "Esta es la fecha de creacion del ticket")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column
    (nullable = false)
    private Date fechaTicket;

    @Schema(description = "Este es el id del soporte que es asignado para el ticket")
    @Column
    (nullable = true )
    private Long idSoporte;

    @Schema(description = "Este es el id del estado del ticket")
    @Column
    (nullable = true )
    private Long idEstado;

    @Schema(description = "Este es el id del usuario que ha abierto el ticket")
     @Column
    (nullable = true )
    private Long idUsuario;
    
    @Schema(description = "Este es el historial del ticket")
    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Historial>historial;

    @Schema(description = "Este es el motivo por el cual se ha abierto el ticket")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false) 
    @JsonIgnoreProperties({"ticket", "hibernateLazyInitializer", "handler"})
    private Motivo motivo;


}
