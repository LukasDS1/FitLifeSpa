package com.example.privileges_service.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Los roles dentro del sistema de fitlifespa")
public class Rol extends RepresentationModel<Rol> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del rol")
    private Long idRol;

    @Column
    (nullable=false,length = 20)
    @Schema(description = "Nombre del rol")
    private String nombre;

    @OneToMany(mappedBy = "rol",cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Que privilegios tiene el rol")
    List<Privileges> privilegios;
}
