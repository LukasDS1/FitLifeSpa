package com.example.payment_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.payment_service.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer>{
    List<Pago> findByIdUsuario(Long idUsuario);
    List<Pago> findByIdEstado(Long idEstado);
    List<Pago> findByIdPlan(Long idPlan);
}