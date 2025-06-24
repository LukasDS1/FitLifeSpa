package com.example.inscripcion_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.inscripcion_service.model.Inscripcion;
import java.util.List;


@Repository
public interface InscripcionRepository extends JpaRepository <Inscripcion,Long>{

    @Query("SELECT i FROM Inscripcion i WHERE :idUsuario IN elements(i.idUsuario)")
    List<Inscripcion> findByUsuarioId(@Param("idUsuario") Long idUsuario);

    List<Inscripcion> findByIdEstado(Long idEstado);
    
    List<Inscripcion> findByIdClase(Long idClase);
    
}
