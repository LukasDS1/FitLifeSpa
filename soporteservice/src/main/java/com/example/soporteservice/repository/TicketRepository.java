package com.example.soporteservice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.soporteservice.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long>{
    List<Ticket> findByIdUsuario(Long idUsuario);
    
}
