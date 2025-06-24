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


    public Boolean validacion(Long id){
        return inscripRepo.existsById(id);
    }


    public void eliminarInscripcion (Long id) {
        if(validacion(id)){
            inscripRepo.deleteById(id);
        }
    }

    public Inscripcion saveInscripcionValidada(Inscripcion insc) {

    if (insc.getIdClase() == null) {
        throw new IllegalArgumentException("La clase no puede ser nula.");
    }
    if (!validarClase(insc.getIdClase())) {
        throw new IllegalArgumentException("La clase especificada no existe.");
    }

    if (insc.getIdEstado() == null) {
        throw new IllegalArgumentException("El estado no puede ser nulo.");
    }
    if (!validarEstado(insc.getIdEstado())) {
        throw new IllegalArgumentException("El estado especificado no existe.");
    }

    if (insc.getIdUsuario() == null || insc.getIdUsuario().isEmpty()) {
        throw new IllegalArgumentException("Debe haber al menos un usuario inscrito.");
    }

    String url_register_service = "http://localhost:8082/api-v1/register/exists/{id}";
    for (Long idUsuario : insc.getIdUsuario()) {
        try {
            @SuppressWarnings("rawtypes")
            Map cliente = restTemplate.getForObject(url_register_service, Map.class, idUsuario);
            if (cliente == null || cliente.isEmpty()) {
                throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe.");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el usuario con ID " + idUsuario + ": " + e.getMessage());
        }
    }

 
    return inscripRepo.save(insc);
}


    public List<Inscripcion> listarIncripcionesPorEstado(Long id){
        return inscripRepo.findByIdEstado(id);
    }

    public List<Inscripcion> listarIncripcionesPorClase(Long id){
        return inscripRepo.findByIdClase(id);
    }

    public List<Inscripcion> listarPorUsuario(Long id){
        return inscripRepo.findByUsuarioId(id);
    }

     public Boolean validarEstado (Long idEstado){
        String url = "http://localhost:8081/api/v1/privilegios/findEstado/{id}";
        try {
            @SuppressWarnings("rawtypes")
            Map objeto = restTemplate.getForObject(url, Map.class, idEstado);

            if (objeto == null || objeto.isEmpty()) {
                return false;
            }
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }


      public Boolean validarClase (Long idClase){
        String url = "http://localhost:8088/api-v1/clase/listarid/{idClase}";
        try {
            @SuppressWarnings("rawtypes")
            Map claseObjeto = restTemplate.getForObject(url, Map.class, idClase);
            
            if (claseObjeto == null || claseObjeto.isEmpty()) {
                return false;
            }
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
}
