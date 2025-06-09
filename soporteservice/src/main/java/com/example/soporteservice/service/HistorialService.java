package com.example.soporteservice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.soporteservice.model.Historial;
import com.example.soporteservice.repository.HistorialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class HistorialService {

    private final HistorialRepository historialRepository;

    public List<Historial> getHistorial(){
        return historialRepository.findAll();
    }

    public Historial getHistorialById(Long idHistorial){
        return historialRepository.findById(idHistorial).orElseThrow();
    }

    
  public List<Historial> getHistorialesByTicketId(Long idTicket) {
    return historialRepository.findByTicketIdTicket(idTicket);
}
    

}
