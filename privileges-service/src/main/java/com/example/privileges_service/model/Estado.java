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
@Table(name = "estado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Los estados de fitlifespa")
public class Estado extends RepresentationModel<Privileges> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id unico del estado")
    private Long idEstado;

    @Column
    @Schema(description = "El nombre del estado")
    private String nombre;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Que estado esta ligado al privilegio")
    List<Privileges> privilegios; 
}
