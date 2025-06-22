package com.example.privileges_service.model;

import java.util.List;

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
import lombok.NoArgsConstructor;
@Entity
@Table(name = "modulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Los modulos existentes de FitLifeSpa")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id unico del modulo")
    private Long idModulo;

    @Column
    @Schema(description = "Una pequeña descripcion del modulo")
    private String descripcion;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "que privilegios tiene este modulo")
    List<Privileges> privilegios;
}
