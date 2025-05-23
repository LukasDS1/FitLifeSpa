package com.example.soporteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soporteservice.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

}
