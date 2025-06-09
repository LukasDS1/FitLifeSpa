package com.example.clase_service.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.clase_service.model.Clase;
import com.example.clase_service.repository.ClaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaseService {

    private final ClaseRepository claseRepository;
    private final RestTemplate restTemplate;
 
    public List<Clase> findByIDService(Long idServicio){  // TODO:dudoso
        return claseRepository.findByIdServicio(idServicio);
    }

    public List<Clase> getAllClases() {
        return claseRepository.findAll();
    }
    public Optional<Clase> getClaseById(Long idClase) { 
        return claseRepository.findById(idClase);

    }

    public Clase saveClase(Clase clase) {
        try {
            if(validarServicio(clase)){
            return claseRepository.save(clase);
            }
            throw new RuntimeException("Servicio con ID: "+clase.getIdServicio()+" no encontrada.");
        } catch (Exception e) {
             throw new RuntimeException("Formato JSON invalido.");
        }
    }

    public void deleteClase(Long idClase) {
        Optional<Clase> clase = claseRepository.findById(idClase);
        try {
            if (clase.isPresent()) {
                claseRepository.deleteById(idClase);
            }  
            throw new RuntimeException("Clase con ID: " + idClase + " No encontrada!");
        } catch (Exception e) {
            throw new RuntimeException("Formato JSON invalido.");
        }
    }
    
    
    public Clase updateClase(Long idClase, Clase clase) {
        Optional<Clase> clase1 = claseRepository.findById(idClase);
        try {
            if (clase1.isPresent()) {
                Clase clase2 = clase1.get();
                clase2.getIdClase();
                clase2.setNombre(clase.getNombre());
                clase2.setDescripcion(clase.getDescripcion());
                clase2.setFechaClase(clase.getFechaClase());
                return claseRepository.save(clase2);    
            } 
            throw new RuntimeException("Clase con ID: "+ idClase + " No encontrada!");
        } catch (Exception e) {
            throw new RuntimeException("Formato JSON invalido.");
        }
    }

    public Boolean validarServicio (Clase clase){
        String url = "http://localhost:8085/api-v1/service/exists/{id}";
        try {
            @SuppressWarnings("rawtypes")
            Map objeto = restTemplate.getForObject(url, Map.class, clase.getIdServicio());

            if (objeto == null || objeto.isEmpty()) {
                throw new RuntimeException("El servicio no existe.");
            }
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El servicio no existe");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener servicio " + e.getMessage());
        }
    } 

   public Map<String, Object> obtenerServicioDeClase(Long idClase) {
    Clase clase = claseRepository.findById(idClase)
        .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + idClase));

    Long idServicio = clase.getIdServicio();

    String url = "http://localhost:8085/api-v1/service/exists/{id}";

    try {
        @SuppressWarnings("unchecked")
        Map<String, Object> servicio = restTemplate.getForObject(url, Map.class, idServicio);

        if (servicio == null || servicio.isEmpty()) {
            throw new RuntimeException("Servicio no encontrado.");
        }

        return servicio;

    } catch (HttpClientErrorException.NotFound e) {
        throw new RuntimeException("Servicio no encontrado.");
    } catch (Exception e) {
        throw new RuntimeException("Error al consultar servicio: " + e.getMessage());
    }
}

}
