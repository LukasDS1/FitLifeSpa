package com.example.gymservices_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gymservices_service.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

}
