package com.example.soporteservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soporteservice.model.Historial;

@Repository
public interface HistorialRepository extends JpaRepository<Historial,Long>{
   List<Historial> findByTicketIdTicket(Long idTicket);
}
