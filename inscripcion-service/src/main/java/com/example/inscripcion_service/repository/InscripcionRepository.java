package com.example.inscripcion_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.inscripcion_service.model.Inscripcion;
import java.util.List;


@Repository
public interface InscripcionRepository extends JpaRepository <Inscripcion,Long>{
    List<Inscripcion> findByIdUsuario(Long idUsuario);

    @Query("SELECT i FROM Inscripcion i where idClase = ?1")
    List<Inscripcion> FindByIdClase (Long idClase);

    @Query("SELECT i FROM Inscripcion i where idEstado = ?1")
    List<Inscripcion> findByIdEstado(Long idEstado);
    
}
