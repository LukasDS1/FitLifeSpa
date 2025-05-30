package com.example.reserva_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reserva_service.model.Reserva;


import java.util.List;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    List<Reserva> findByIdUsuario(Long idUsuario);
}
