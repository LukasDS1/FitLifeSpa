package com.example.soporteservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.repository.TicketRepository;
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


    public Ticket createTicket(Ticket ticket){
        if(validarUsuario(ticket.getIdUsuario()) != null){
            if(validarEstado(ticket.getIdEstado()) != null){
                Long estado = ticket.getIdEstado();
                ticket.setIdEstado(estado);
                return ticketRepository.save(ticket);
            }
             throw new RuntimeException("Email no encontrado.");
        }
         throw new RuntimeException("Estado no encontrado o no válido.");
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


   public String validarEstado(Long idEstado) {
    String url = "http://localhost:8081/api/v1/privilegios/findEstado/{id}";

    try {
        Map<?, ?> objeto = restTemplate.getForObject(url, Map.class, idEstado);

        if (objeto == null || objeto.isEmpty()) {
            throw new RuntimeException("El estado no existe");
        }

        Object idObject = objeto.get("idEstado"); 
        if (idObject == null) {
            throw new RuntimeException("El estado no contiene ID");
        }

        Long estadoId = Long.valueOf(idObject.toString());

        return estadoId == 1 ? "Activo" : "Inactivo";

    } catch (HttpClientErrorException.NotFound e) {
        throw new RuntimeException("El estado no existe");
    } catch (Exception e) {
        throw new RuntimeException("Error al obtener el estado: " + e.getMessage());
    }
}





}
