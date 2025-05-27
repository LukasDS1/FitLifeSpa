package com.example.soporteservice.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.soporteservice.model.Estado;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.service.TicketService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/estado")
    public ResponseEntity<?> getEstadoTicket(@RequestBody Ticket ticket){
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
            Estado stateEstado = ticketService.existEstado(idEstado); 
            return ResponseEntity.ok(stateEstado);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el estado");
        }
        }

        @PostMapping("/creartk")
        public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {
            try {
                if (ticket.getFechaTicket() == null) {
                    return ResponseEntity.badRequest().body("El campo Fecha ticket es requerido ");
                }

                if (ticket.getMotivo() == null) {
                    return ResponseEntity.badRequest().body("El campo motivo es requerido");
                }

                if (ticket.getMotivo().getIdMotivo() == null) {
                    return ResponseEntity.badRequest().body("El ID del motivo es requerido");
                }

                ticketService.createTicket(ticket);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("El ticket ha sido creado con exito");

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
            }
        }

        @GetMapping("/listartks")
        public ResponseEntity<List<Ticket>> getAllTickets() {
            try {
                return ResponseEntity.ok(ticketService.getAllTicket());
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        @GetMapping("/listartkid/{idTicket}")
        public ResponseEntity<?> getIdTicket(@PathVariable Long idTicket) {
            try {
                Optional<Ticket> exist = ticketService.getById(idTicket); 
                if(exist.isPresent()){
                    return ResponseEntity.ok().body(exist.get());
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket con ID "+idTicket+" no encontrado");
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    

















}

    



