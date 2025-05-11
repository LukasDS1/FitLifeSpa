package com.example.register_service.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column 
    (nullable = false,length = 30,unique = true)
    private String correo;

    @Column
    (nullable = false)
    private String password;    

    @Column
    (nullable = false,length = 50)
    private  String nombre;

    @Column
    (nullable = false,length = 50)
    private String apellidoPaterno;

    @Column
    (nullable = false,length = 50)
    private String apellifoMaterno;

    @Column
    (nullable = false,length = 10)
    private String genero;

    @Column
    (nullable = false, length = 12)
    private String rut;

    @ManyToOne
    @JoinColumn(name = "idRol")
    @JsonIgnoreProperties("users")
    private Rol rol;

    
    
}
