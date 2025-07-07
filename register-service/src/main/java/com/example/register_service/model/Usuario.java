package com.example.register_service.model;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(description = "Clase usuario la cual sera utilizada para modelar un usuario con todos sus atributos")
@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Usuario extends RepresentationModel<Usuario>{
    @Schema(description = "ID unico del usuario")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Schema(description = "Email del usuario")
    @Column 
    (nullable = false,length = 50,unique = true)
    private String email;

    @Schema(description = "Contraseña del usuario")
    @Column
    (nullable = false,length = 255)
    private String password;    

    @Schema(description = "Nombre del usuario") 
    @Column
    (nullable = false,length = 50)
    private  String nombre;

    @Schema(description = "Apellido paterno del usuario")
    @Column
    (nullable = false,length = 50)
    private String apellidoPaterno;

    @Schema(description = "Apellido materno del usuario")
    @Column
    (nullable = true,length = 50)
    private String apellidoMaterno;

    @Schema(description = "Genero del usuario")
    @Column
    (nullable = false,length = 20)
    private String genero;

    @Schema(description = "Rut del usuario")
    @Column
    (nullable = false, length = 12)
    private String rut;

     @Schema(description = "Rol al cual pertenece el usuario")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRol")
    @JsonIgnoreProperties("usuarios")
    private Rol rol;
    
}
