package com.example.membresia_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.membresia_service.model.Membresia;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia,Long>{

    @Query("SELECT m FROM Membresia m WHERE :idUsuario IN elements(m.idUsuario)")
    List<Membresia> findByUsuarioId(@Param("idUsuario") Long idUsuario);
    
    List<Membresia> findByPlan_IdPlan(Long idPlan); 

}
