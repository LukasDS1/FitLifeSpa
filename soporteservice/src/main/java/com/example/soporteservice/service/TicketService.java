package com.example.soporteservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.repository.TicketRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final RestTemplate restTemplate;

    public List<Ticket> getAllTicket() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getById(Long idTicket) {
        return ticketRepository.findById(idTicket);
    }

    public Ticket createTicket(Ticket ticket) {
    if (!validarUsuario(ticket.getIdUsuario())) {
        throw new RuntimeException("Usuario no encontrado.");
    }

    if (!validarEstado(ticket.getIdEstado())) {
        throw new RuntimeException("Estado no encontrado o no válido.");
    }

    return ticketRepository.save(ticket);

    }

    public List<Ticket> findbyidUsuario(Long idUsuario){
    return ticketRepository.findByIdUsuario(idUsuario);
    }



    public Boolean validarUsuario(Long idUsuario){
        String url = "http://localhost:8082/api-v1/register/exists/{id}";
        try {
            @SuppressWarnings("rawtypes")
            Map objeto = restTemplate.getForObject(url, Map.class, idUsuario);

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

   public String obtenerEstadoDelTicket(Long idTicket) {
    Optional<Ticket> ticketOptional = ticketRepository.findById(idTicket);
    if (ticketOptional.isEmpty()) {
        throw new EntityNotFoundException("No se encontró el ticket con ID " + idTicket);
    }

    Ticket ticket = ticketOptional.get();
    Long idEstado = ticket.getIdEstado();

    String url = "http://localhost:8081/api/v1/privilegios/findEstado/{id}";

    try {
        Map<String, Object> response = restTemplate.getForObject(url, Map.class, idEstado);
        if (response == null || !response.containsKey("nombre")) {
            throw new RuntimeException("No se pudo obtener el estado del ticket");
        }

        return response.get("nombre").toString();
    } catch (HttpClientErrorException.NotFound e) {
        throw new EntityNotFoundException("Estado no encontrado con ID " + idEstado);
    } catch (Exception e) {
        throw new RuntimeException("Error al consultar el estado: " + e.getMessage());
    }
}

public boolean validarEstado(Long idEstado) {
    String url = "http://localhost:8081/api/v1/privilegios/findEstado/{id}";

    try {
        Map<String, Object> response = restTemplate.getForObject(url, Map.class, idEstado);
        return response != null && response.containsKey("nombre");
    } catch (HttpClientErrorException.NotFound e) {
        return false;
    } catch (Exception e) {
        return false;
    }
}







}
