package com.example.soporteservice.service;



import org.springframework.stereotype.Service;
import com.example.soporteservice.model.Estado;
import com.example.soporteservice.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public Estado getEstado(Long idEstado) {
        return estadoRepository.findById(idEstado).orElseThrow();
    }


    
    public String stateEstado(Long idEstado){
        if (idEstado == null) {
            throw new IllegalArgumentException("Estado no valido");
        }
        if (idEstado  == 1) {
            return "Activo";
        }
        if (idEstado == 2) {
            return "Inactivo";
        }
        throw new RuntimeException("Estado no valido");
    }


}
