package com.example.membresia_service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;

    @Column
    (nullable = false,length = 50)
    private String nombre;

    @Column
    (nullable = false,length = 200)
    private String descripcion;

    @Column
    (nullable = false)
    private int Costo;

    @Column
    (nullable = false)
    private int duracion;

    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Membresia> membresia;

    
    


}
