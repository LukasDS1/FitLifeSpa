package com.example.inscripcion_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.inscripcion_service.model.Inscripcion;
import com.example.inscripcion_service.repository.InscripcionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InscripcionService {
    
    private final InscripcionRepository inscripRepo;

    private final RestTemplate restTemplate;

    public List<Inscripcion> listarInscripcion (){
        return inscripRepo.findAll();
    }

    public Inscripcion buscarporId(Long id){
        return inscripRepo.findById(id).get();
    }

    public Inscripcion agragarInscripcion1(Inscripcion insc) {
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


    public Inscripcion agragarInscripcion(Inscripcion insc) {
        String url_register_service = "http://localhost:8082/api-v1/register/exists/{id}";
        try {
            Map cliente = restTemplate.getForObject(url_register_service, Map.class, insc.getIdUsuario());

            if (cliente == null || cliente.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
            }
            return inscripRepo.save(insc);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El usuario no existe");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el cliente: " + e.getMessage());
        }
    }
}
