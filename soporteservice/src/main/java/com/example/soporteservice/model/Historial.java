package com.example.soporteservice.model;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "historial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 

public class Historial {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @Column
    (nullable = false,length = 200)
    private String mensaje;

    @Column
    (nullable = false,length = 200 )
    private String tipo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column
    (nullable = false) 
    private Date fechaMensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTicket")
    @JsonBackReference
    private Ticket ticket;


    
    

}
