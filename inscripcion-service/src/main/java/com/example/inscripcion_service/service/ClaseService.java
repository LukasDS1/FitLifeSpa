package com.example.inscripcion_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.inscripcion_service.repository.ClaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClaseService {
    @Autowired
    private ClaseRepository claseRepo;

    @Autowired 
    RestTemplate restTemplate;

    
    public Boolean validaClase(Long id) {
        return claseRepo.existsById(id);
    }
}
