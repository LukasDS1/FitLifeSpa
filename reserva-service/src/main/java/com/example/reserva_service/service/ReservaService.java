package com.example.reserva_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.reserva_service.model.Reserva;
import com.example.reserva_service.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservaService {
    
    private final ReservaRepository reservaRepository;

    private final RestTemplate client;

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Long id){
        return reservaRepository.findById(id).get();
    }

    public Reserva agregarReserva(Reserva reserva){
        if(validarServicio(reserva) == true && validarUsuario(reserva) == true){
            return reservaRepository.save(reserva);
        }
        return null;
    }

    public void borrarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> listarPorIdUser(Long id){
        return reservaRepository.findByIdUsuario(id);
    }

    public Boolean validarUsuario(Reserva reserva){
        String url = "http://localhost:8082/api-v1/register/exists/{id}";
        try {
            @SuppressWarnings("rawtypes")
            Map objeto = client.getForObject(url, Map.class, reserva.getIdUsuario());

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

    public Boolean validarServicio (Reserva reserva){
        String url = "http://localhost:8085/api-v1/service/exists/{id}";
        try {
            @SuppressWarnings("rawtypes")
            Map objeto = client.getForObject(url, Map.class, reserva.getIdServicio());

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

    public Boolean validarService(Long id){
        return reservaRepository.existsById(id);
    }
}
