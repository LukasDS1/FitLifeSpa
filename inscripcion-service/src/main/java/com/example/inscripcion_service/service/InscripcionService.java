package com.example.inscripcion_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inscripcion_service.model.Inscripcion;
import com.example.inscripcion_service.repository.InscripcionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripRepo;

    public List<Inscripcion> listarInscripcion (){
        return inscripRepo.findAll();
    }

    public Inscripcion buscarporId(Long id){
        return inscripRepo.findById(id).get();
    }

    public Inscripcion agragarInscripcion (Inscripcion insc) {
        return inscripRepo.save(insc);
    }


    public Boolean validacion(Long id){
        return inscripRepo.existsById(id);
    }


    public void eliminarInscripcion (Long id) {
        if(validacion(id)){
            inscripRepo.deleteById(id);
        }
    }
    
}
