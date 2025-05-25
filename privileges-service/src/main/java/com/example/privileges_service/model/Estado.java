package com.example.privileges_service.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "estados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Privileges> privilegios; 
}
