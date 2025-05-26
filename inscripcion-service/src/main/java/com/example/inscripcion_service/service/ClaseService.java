package com.example.inscripcion_service.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.inscripcion_service.model.Clase;
import com.example.inscripcion_service.repository.ClaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClaseService {
    
    @SuppressWarnings("unused")
    private final ClaseRepository claseRepo;

    public final RestTemplate client;

    public Boolean validarClase (Clase clase){
        String url = "http://localhost:8088/api-v1/listarid/{idClase}";
        try {
            @SuppressWarnings("rawtypes")
            Map objeto = client.getForObject(url, Map.class, clase.getIdClase());

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
    
}
