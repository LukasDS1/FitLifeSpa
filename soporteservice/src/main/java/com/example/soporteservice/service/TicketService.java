package com.example.soporteservice.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.soporteservice.model.Estado;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.model.Usuario;
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

    public Ticket createTicket(Ticket ticket) {
        if (existEstado(ticket.getEstado().getIdEstado()) != null) {
            if (exist(ticket.getUsuario().getIdUsuario()) != null) {
                Estado estado = ticket.getEstado();
                ticket.setEstado(estado);
                return ticketRepository.save(ticket);
            }
            throw new RuntimeException("Email no encontrado.");
        }
        throw new RuntimeException("Estado no encontrado o no válido.");
    }

   
    public Usuario exist(Long idUsuario) {
        String url_register_service = "http://localhost:8082/api-v1/register/exists/{idUsuario}";
        try {
            Usuario Usuarioexist = restTemplate.getForObject(url_register_service, Usuario.class, idUsuario);
            System.out.println("Usuario encontrado: " + Usuarioexist);
            return Usuarioexist;
        } catch (HttpClientErrorException e) {
            System.out.println("Error HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar existencia del usuario" + e.getMessage());
        }
    }

    public Estado existEstado(Long idEstado) {

        String url_register_service = "http://localhost:8081/api/v1/privilegios/findEstado/{id}";

        try {
            Estado EstadoState = restTemplate.getForObject(url_register_service, Estado.class, idEstado);
            System.out.println("Estado del Ticket " + EstadoState);
            return EstadoState;
        } catch (HttpClientErrorException e) {
            System.out.println("Error HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar el estado del Ticket" + e.getMessage());
        }
    }
    

    

 














}
