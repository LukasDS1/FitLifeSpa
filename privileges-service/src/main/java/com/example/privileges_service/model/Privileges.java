package com.example.privileges_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "privilegios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Privileges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrivilege;
    
    @ManyToOne
    @JoinColumn(name = "idRol")
    @JsonIgnoreProperties("privilegios")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idEstado")
    @JsonIgnoreProperties("privilegios")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "idModulo")
    @JsonIgnoreProperties("privilegios")
    private Modulo modulo;
}
