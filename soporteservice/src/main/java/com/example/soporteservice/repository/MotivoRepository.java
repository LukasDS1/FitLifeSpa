package com.example.soporteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soporteservice.model.Motivo;

@Repository
public interface MotivoRepository extends JpaRepository<Motivo,Long>{

}
