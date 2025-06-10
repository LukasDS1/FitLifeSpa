package com.example.resena_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.resena_service.model.Resenia;
import java.util.List;


@Repository
public interface ReseniaRepository extends JpaRepository <Resenia,Long>{
    List<Resenia> findByIdServicio(Long idServicio);
}
