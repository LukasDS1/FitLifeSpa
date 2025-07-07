package com.example.soporteservice.model;


import java.util.List;

import org.springframework.hateoas.RepresentationModel;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Schema(description = "Este es modelo del Motivo")
@Entity
@Table(name = "motivo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Motivo extends RepresentationModel<Motivo>{
    @Schema(description = "Este es el ID unico del motivo")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idMotivo;
    @Schema(description = "Este es la descripcion del motivo")
    @Column
    (nullable = false,length = 200)
    private String Descripcion;

    @Schema(description = "Este es la lista de tickets que pertenecen al motivo")
    @OneToMany(mappedBy = "motivo",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ticket>ticket;

}
