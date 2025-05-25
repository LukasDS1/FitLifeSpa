package com.example.clase_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.clase_service.model.Servicio;
import com.example.clase_service.repository.ServicioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioService {
    
    private final ServicioRepository servicioRepository;

    public List<Servicio> getAllService(){
        return servicioRepository.findAll();
    }


    public Optional<Servicio> getServicioByID(Long idServicio){
        return servicioRepository.findById(idServicio);
    }


    public Servicio saveService(Servicio servicio){
        return servicioRepository.save(servicio);
    }

    public Boolean deleteService(Long idServicio){
        Optional<Servicio> exist = servicioRepository.findById(idServicio);
        if(exist.isEmpty()){
            return false;
        }else{
            servicioRepository.deleteById(idServicio);
            return true;
        }
    }


    public Servicio createService(Servicio servicio){
            return servicioRepository.save(servicio);
        }


    public Servicio updateService(Long idServicio,Servicio servicio){
        Optional<Servicio> exist = servicioRepository.findById(idServicio);
        if(exist.isEmpty()){
            throw new RuntimeException("Servicio con ID: "+idServicio+" No ha sido encontrado!");
        }else{
            Servicio servicio2 = exist.get();
            servicio2.getIdServicio();
            servicio2.setNombre(servicio.getNombre());
            servicio2.setDescripcion(servicio.getDescripcion());
            servicio2.setClases(servicio.getClases());
            return servicioRepository.save(servicio2);
        }

    }






}


