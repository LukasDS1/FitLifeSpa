package com.example.payment_service.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;
    @Column(nullable = false)
    private int neto;

    @Column(nullable = false)
    private int iva;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false)
    private Date fechaPago;

    @Column(nullable = false, unique = true)
    private Long idUsuario;

    @Column (nullable = false, unique = true)
    private Long idPlan;
    
    @Column(nullable = false, unique = true)
    private Long idEstado;
}
