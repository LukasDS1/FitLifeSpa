package com.example.register_service.model;

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

@Schema(description = "Clase Rol donde se obtienen todos los roles al que pertenecen los usuarios")
@Entity
@Table(name = "rol")
@NoArgsConstructor
@AllArgsConstructor
@Data 
@EqualsAndHashCode(callSuper = false)
public class Rol extends RepresentationModel<Rol>{
    @Schema(description = "Identificador unico del rol")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Schema(description = "Nombre del rol")
    @Column
    (nullable = false,length = 20)
    private String nombre;

    @Schema(description = "Lista de usuarios los cual pertenecen a un rol")
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Usuario> users;
    
}
