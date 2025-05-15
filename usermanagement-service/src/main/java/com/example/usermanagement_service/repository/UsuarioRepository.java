package com.example.usermanagement_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.usermanagement_service.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    Optional<Usuario> findByEmail (String email);
    Optional <Usuario> deleteByEmail (String email);

}
