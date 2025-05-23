package com.example.soporteservice.model;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "motivo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Motivo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idMotivo;

    @Column
    (nullable = false,length = 200)
    private String Descripcion;

    @OneToMany(mappedBy = "motivo",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ticket>ticket;

}
