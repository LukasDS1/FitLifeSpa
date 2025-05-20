package com.example.clase_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.clase_service.model.Clase;
import com.example.clase_service.repository.ClaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaseService {

    private final ClaseRepository claseRepository;

    public List<Clase> getClases() {
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

    // TODO: modificar para actualizar atributos por separado
    /**
     * @return object Clase if it's present
     */
    public Clase updateClase(Long idClase, Clase clase) {
        Optional<Clase> clase1 = claseRepository.findById(idClase);
        if (clase1.isEmpty()) {
            return clase1.get();
        }
        return claseRepository.save(clase);
    }
}
