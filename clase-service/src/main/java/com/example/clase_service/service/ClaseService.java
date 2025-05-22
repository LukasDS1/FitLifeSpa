package com.example.clase_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.clase_service.model.Clase;
import com.example.clase_service.repository.ClaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaseService {

    private final ClaseRepository claseRepository;

    public List<Clase> getAllClases() {
        return claseRepository.findAll();
    }

    public Optional<Clase> getClaseById(Long idClase) {
        return claseRepository.findById(idClase);
    }

    public Clase saveClase(Clase clase) {
        return claseRepository.save(clase);
    }

    public Boolean deleteClase(Long idClase) {
        Optional<Clase> clase = claseRepository.findById(idClase);
        if (clase.isEmpty()) {
            return false;
        }
        claseRepository.deleteById(idClase);
        return true;
    }
    
    public Clase updateClase(Long idClase, Clase clase) {
        Optional<Clase> clase1 = claseRepository.findById(idClase);
        if (clase1.isEmpty()) {
            throw new RuntimeException("Clase con ID: "+idClase+" No encontrada!"); 
        } else {
            Clase clase2 = clase1.get();
            clase2.getIdClase();
            clase2.setNombre(clase.getNombre());
            clase2.setDescripcion(clase.getDescripcion());
            clase2.setFechaClase(clase.getFechaClase());
            clase2.getServicio();
            return claseRepository.save(clase2);
        }

    }
}
