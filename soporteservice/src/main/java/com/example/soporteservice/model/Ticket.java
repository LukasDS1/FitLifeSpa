package com.example.soporteservice.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.NoArgsConstructor;

@Entity
@Table(name="ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column
    (nullable = false)
    private Date fechaTicket;
    
    @Column
    (nullable = true )
    private Long idSoporte;

    @Column
    (nullable = true )
    private Long idEstado;

     @Column
    (nullable = true )
    private Long idUsuario;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Historial>historial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false) 
    @JsonIgnoreProperties({"ticket", "hibernateLazyInitializer", "handler"})
    private Motivo motivo;


}
