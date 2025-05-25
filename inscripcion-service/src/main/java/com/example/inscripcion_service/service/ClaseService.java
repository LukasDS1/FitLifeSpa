package com.example.inscripcion_service.service;

import org.springframework.stereotype.Service;
import com.example.inscripcion_service.repository.ClaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClaseService {
    
    private final ClaseRepository claseRepo;

   
    
    public Boolean validaClase(Long id) {
        return claseRepo.existsById(id);
    }
}
