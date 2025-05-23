package com.example.inscripcion_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inscripcion_service.repository.EstadoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepo;
    
    public Boolean validaEstado(Long id){
        return estadoRepo.existsById(id);
    }
}
