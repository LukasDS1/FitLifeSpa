package com.example.gymservices_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.gymservices_service.model.Servicio;
import com.example.gymservices_service.repository.ServicioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public Servicio addService(Servicio servicio) {
        if (servicio != null) {
            return servicioRepository.save(servicio);
        }
        throw new RuntimeException("Error al añadir el servicio.");
    }

    /**
     * 
     * @param idServicio
     * @return clase servicio o un runtimeException()
     * 
     */
    public Servicio serviceById(Long idServicio) {
        Optional<Servicio> servicio1 = servicioRepository.findById(idServicio);
        if (servicio1.isPresent()) {
            return servicio1.get();
        }
        return null;
    }

    public Boolean deleteService(Long idServicio) {
        try {
            if (servicioRepository.existsById(idServicio)) {
                servicioRepository.deleteById(idServicio);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió una excepción inesperada: " + e.getMessage());
        }
    }

    public Servicio updateService(Long idServicio, Servicio newServicio) {
        Boolean currService = servicioRepository.existsById(idServicio);
        if (!currService) {
            return null;
        }

        Servicio currentService = servicioRepository.findById(idServicio).get();

        currentService.setIdServicio(idServicio);
        currentService.setNombre(newServicio.getNombre());
        currentService.setDescripcion(newServicio.getDescripcion());
        servicioRepository.save(currentService);
        return currentService;
    }
    
}
