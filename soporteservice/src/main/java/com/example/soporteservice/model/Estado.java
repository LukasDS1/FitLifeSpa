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
@Table(name = "estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Estado {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idEstado;

    @Column
    (nullable = false,length = 20)
    private String nombre;

   @OneToMany(mappedBy = "estado",cascade = CascadeType.ALL)
   @JsonIgnore
   private List<Ticket>ticket;

    

}
