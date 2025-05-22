package com.example.membresia_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.membresia_service.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

}
