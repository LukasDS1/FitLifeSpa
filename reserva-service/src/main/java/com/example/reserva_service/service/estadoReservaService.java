package com.example.reserva_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.reserva_service.model.estadoReserva;
import com.example.reserva_service.repository.estadoReservaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class estadoReservaService {
   
    private final estadoReservaRepository estadoReservaRepo;

    private final RestTemplate client;

    public List<estadoReserva> listarTodo(){
        return estadoReservaRepo.findAll();
    }

    public estadoReserva listarPorId(Long id){
        return estadoReservaRepo.findById(id).get();
    }

    public estadoReserva agregarEstadoReserva (estadoReserva estado){
        if (validarEstado(estado)) {
            return estadoReservaRepo.save(estado);
        }
        return null;
    }

    public Boolean validarEstado (estadoReserva estado){
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

    public void eliminarEstadoReserva(Long id){
        if (estadoReservaRepo.existsById(id)) {
            estadoReservaRepo.deleteById(id);
        }
    }
}
