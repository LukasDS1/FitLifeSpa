package com.example.privileges_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.privileges_service.model.Rol;
import com.example.privileges_service.repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public Rol validarRol(Long id){
        if(rolRepository.existsById(id)){
            Rol rol = rolRepository.findById(id).get();
            return rol;
        }
        return null;
    }

    public Rol AgregarRol(Rol rol){
        return rolRepository.save(rol);
    }

}
