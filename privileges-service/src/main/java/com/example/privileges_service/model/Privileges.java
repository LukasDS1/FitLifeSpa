package com.example.privileges_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "privilegio")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Los privilegios de FitLifeSPA")
public class Privileges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico de privilegio")
    private Long idPrivilege;

    @Column
    @Schema(description = "Esto demuestra si el privilegio esta activo o no")
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "idRol")
    @JsonIgnoreProperties("privilegios")
    @Schema(description = "Los roles que tienen este privilegio")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idEstado")
    @JsonIgnoreProperties("privilegios")
    @Schema(description = "El estado de este privilegio")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "idModulo")
    @JsonIgnoreProperties("privilegios")
    @Schema(description = "A que modulo pertenece")
    private Modulo modulo;
}
