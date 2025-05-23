package com.example.soporteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soporteservice.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,Long> {

}
