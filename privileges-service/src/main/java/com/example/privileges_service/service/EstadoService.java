package com.example.privileges_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.privileges_service.model.Estado;
import com.example.privileges_service.repository.EstadoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepo;

    public Estado validarEstado(Long id){
        if(estadoRepo.existsById(id)){
            Estado estado = estadoRepo.findById(id).get();
            return estado;
        }
        return null;
    }

    public Estado agregarEstado(Estado estado) {
        return estadoRepo.save(estado);
    }
}
