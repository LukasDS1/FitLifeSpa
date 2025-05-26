package com.example.inscripcion_service.service;


import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.inscripcion_service.model.Estado;
import com.example.inscripcion_service.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EstadoService {
    
    @SuppressWarnings("unused")
    private final EstadoRepository estadoRepo;
    
    private final RestTemplate client;
    
    public Boolean validarEstado (Estado estado){
        String url = "http://localhost:8081/api/v1/privilegios/findEstado/{id}";
        try {
            @SuppressWarnings("rawtypes")
            Map objeto = client.getForObject(url, Map.class, estado.getIdEstado());

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
