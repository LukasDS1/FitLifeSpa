package com.example.usermanagement_service.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idUsuario;

    @Column
    (nullable = false,length = 225,unique = true)
    private String email;

    @Column
    (nullable = false,length = 200)
    private String password;

    @Column
    (nullable = false,length = 50)
    private String nombre;

    @Column
    (nullable = false,length = 50)
    private String apellidoPaterno;

    @Column
    (nullable = false,length = 50)
    private String apellidoMaterno;

    @Column
    (nullable = false,length = 10)
    private String genero;

    @Column
    (nullable = false,length = 12)
    private String rut;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "idRol")
    @JsonIgnoreProperties("usuarios")
    private Rol rol;





}
