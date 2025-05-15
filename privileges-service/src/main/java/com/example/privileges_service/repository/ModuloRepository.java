package com.example.privileges_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.privileges_service.model.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository <Modulo,Long>{

}
