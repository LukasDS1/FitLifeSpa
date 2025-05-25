package com.example.privileges_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.privileges_service.model.Modulo;
import com.example.privileges_service.repository.ModuloRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ModuloService {
    
    private final ModuloRepository modRepo;

    public Modulo validarModulo(Long id){
        if (modRepo.existsById(id)){
            Modulo modulo = modRepo.findById(id).get();
            return modulo;
        }
        return null;
    }

    public Modulo agregarModulo(Modulo modulo){
        return modRepo.save(modulo);
    }
}
