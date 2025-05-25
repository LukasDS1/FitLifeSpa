package com.example.inscripcion_service.service;


import org.springframework.stereotype.Service;
import com.example.inscripcion_service.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EstadoService {
    
    private final EstadoRepository estadoRepo;
    
    public Boolean validaEstado(Long id){
        return estadoRepo.existsById(id);
    }
}
