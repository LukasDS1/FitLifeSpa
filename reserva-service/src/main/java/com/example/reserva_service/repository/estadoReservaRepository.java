package com.example.reserva_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reserva_service.model.estadoReserva;

@Repository
public interface estadoReservaRepository extends JpaRepository<estadoReserva, Long>{

}
