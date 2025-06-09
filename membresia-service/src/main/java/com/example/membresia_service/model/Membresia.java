package com.example.membresia_service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "membresia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Membresia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMembresia;

    @Column
    (nullable=false,length = 50)
    private String nombre;

    @Column
    (nullable = false, length = 200)
    private String descripcion;

    @ElementCollection
    @CollectionTable(name = "membresia_usuarios", joinColumns = @JoinColumn(name = "id_membresia"))
    @Column(name = "id_usuario")
    private List<Long> idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlan")
    @JsonIgnore
    private Plan plan;

}
