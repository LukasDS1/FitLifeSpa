package com.example.membresia_service.model;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import io.swagger.v3.oas.annotations.media.Schema;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Schema(description = "Modelo de la membresia")
@Entity
@Table(name = "membresia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(callSuper = false)
public class Membresia extends RepresentationModel<Membresia> {
    @Schema(description = "ID unico de la membresia")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMembresia;

    @Schema(description = "nombre de la membresia")
    @Column
    (nullable=false,length = 50)
    private String nombre;

    @Schema(description = "descripcion de la membresia")
    @Column
    (nullable = false, length = 200)
    private String descripcion;

    @Schema(description = "Coleccion de usuarios los cuales pertenecen a una membresia ")
    @ElementCollection
    @CollectionTable(name = "membresia_usuarios", joinColumns = @JoinColumn(name = "id_membresia"))
    @Column(name = "id_usuario")
    private List<Long> idUsuario;

    @Schema(description = "Id del plan al cual pertenece la membresia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlan")
    private Plan plan;

}
