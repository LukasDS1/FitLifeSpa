package com.example.soporteservice.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.service.EstadoService;
import com.example.soporteservice.service.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor
public class TicketController {


    private final TicketService ticketService;
    private final EstadoService estadoService;

   
    
    @PostMapping("/estado")
    public ResponseEntity<String> getEstadoTicket(@RequestBody Ticket ticket){
        Optional<Ticket> exist = ticketService.getById(ticket.getIdTicket());   
        if(!exist.isPresent()){                                                 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket con ID: "+ ticket.getIdTicket()+ " no existe");
        }

        Ticket ticket1 = exist.get();

        if(ticket1.getEstado() == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket con ID: "+ ticket1.getIdTicket()+" no existe");
        }
        try {
            Long idEstado = ticket1.getEstado().getIdEstado();
            String stateEstado = estadoService.stateEstado(idEstado);
            return ResponseEntity.ok(stateEstado);
        } catch (Exception e) {
            throw new RuntimeException("erro al obtener el estado");
        }
        
    }

  
   @PostMapping("/creartk") 
public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {
    try { 
        // Validar si motivo es null primero
        if (ticket.getMotivo() == null) {
            return ResponseEntity.badRequest().body("El campo 'motivo' es requerido.");
        }
        // Luego validar si idMotivo es null
        if (ticket.getMotivo().getIdMotivo() == null) {
            return ResponseEntity.badRequest().body("El ID del motivo es requerido.");
        }
        
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    } catch (Exception e) {
        e.printStackTrace(); // ¡IMPORTANTE! Esto muestra el error real en los logs
        return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
    }
}


    @GetMapping("/listartks")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        try {
            return ResponseEntity.ok(ticketService.getAllTicket());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/listartkid")
    public ResponseEntity<Ticket> getIdTicket(@RequestBody Ticket ticket) {
       try {
            ticketService.getById(ticket.getIdTicket());
         return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticket);
       } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }
    
    

















}

    



