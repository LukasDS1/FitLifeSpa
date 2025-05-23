package com.example.soporteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soporteservice.model.Rol;

@Repository
public interface RolRepository extends JpaRepository <Rol,Long>{

}
