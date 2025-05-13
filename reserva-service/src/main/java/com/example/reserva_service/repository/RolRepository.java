package com.example.reserva_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reserva_service.model.Rol;

@Repository
public interface RolRepository extends JpaRepository <Rol,Long>{

}
