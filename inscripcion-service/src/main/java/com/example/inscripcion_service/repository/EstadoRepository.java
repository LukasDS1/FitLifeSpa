package com.example.inscripcion_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inscripcion_service.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository <Estado,Long>{

}
