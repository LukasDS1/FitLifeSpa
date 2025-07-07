package com.example.soporteservice.model;



import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Schema(description = "Este es el historial del motivo")
@Entity
@Table(name = "historial")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 

public class Historial extends RepresentationModel<Historial>{
    @Schema(description = "Este es el ID unico del historial")
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @Schema(description = "Este es el mensaje del historial")
    @Column
    (nullable = false,length = 200)
    private String mensaje;

    @Schema(description = "Este es el tipo de historial")
    @Column
    (nullable = false,length = 200 )
    private String tipo;

    @Schema(description = "Este es la fecha de creacion del mensaje")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column
    (nullable = false) 
    private Date fechaMensaje;
    @Schema(description = "Este es la iD del ticket al cual pertenece el motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTicket")
    @JsonBackReference
    private Ticket ticket;


}
