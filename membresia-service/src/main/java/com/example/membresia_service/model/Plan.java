package com.example.membresia_service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@Schema(description = "Modelos de plan")
@Entity
@Table(name = "plan")
@Data
@NoArgsConstructor
@AllArgsConstructor     
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Plan {

    @Schema(description = "ID unico para el plan")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;

    @Schema(description = "Nombre del plan")
    @Column
    (nullable = false,length = 50)
    private String nombre;

    @Schema(description = "descripcion del plan ")
    @Column
    (nullable = false,length = 200)
    private String descripcion;

    @Schema(description = "Costo del plan")
    @Column
    (nullable = false)
    private Integer Costo;

    @Schema(description = "Duracion del plan")
    @Column
    (nullable = false)
    private Integer duracion;

    @Schema(description = "Lista de membresias las cual pertenecen a un plan")
    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Membresia> membresia;    


}
