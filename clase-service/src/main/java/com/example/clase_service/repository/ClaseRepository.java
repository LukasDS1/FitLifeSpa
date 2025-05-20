package com.example.clase_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clase_service.model.Clase;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long>{

}
