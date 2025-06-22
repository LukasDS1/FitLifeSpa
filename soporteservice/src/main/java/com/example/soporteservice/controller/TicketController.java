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
import org.springframework.web.client.HttpClientErrorException;


import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;



    @Operation( summary = "Este endpoint permite filtrar los tickets pertenecientes a un usuario" )
    @ApiResponses(value = { @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica que no ha encontrado al usuario",
    content = @Content(schema = @Schema(implementation = Ticket.class))),
    @ApiResponse(responseCode = "202",description = "ACCEPTED: Indica que el usuario ha sido encontrado pero no tiene tickets asignados ",
    content = @Content(schema = @Schema(implementation = Ticket.class))),
    @ApiResponse(responseCode = "200",description = "OK: Indica que el usuario ha sido encontrado y muestra los tickets que tiene asignados ",
    content = @Content(schema = @Schema(implementation = Ticket.class)))
    }
    )
     @GetMapping("usuario/{idUsuario}")
    public ResponseEntity<?> getMembresiasPorUsuario(@PathVariable Long idUsuario) {
    boolean existe = ticketService.validarUsuario(idUsuario);
    if (!existe) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario con ID " + idUsuario + " no existe.");
    }
    List<Ticket> tickets = ticketService.findbyidUsuario(idUsuario);
    if (tickets.isEmpty()) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("El usuario existe pero no tiene tickets asignados.");
    }
    return ResponseEntity.ok(tickets);
}

    @Operation( summary = "Este endpoint permite consultar el estado del ticket (Activo/Inactivo)" )
    @ApiResponses(value = { @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica que no se ha encontrado el estado",
    content = @Content(schema = @Schema(implementation = Ticket.class))),
    @ApiResponse(responseCode = "200",description = "OK: Indica que el estado ha sido encontrado y muestra el estado del ticket",
    content = @Content(schema = @Schema(implementation = Ticket.class)))
    }
    )
    @GetMapping("/estado/{idTicket}")
    public ResponseEntity<String> getEstadoDelTicket(@PathVariable Long idTicket) {
    try {
        String estado = ticketService.obtenerEstadoDelTicket(idTicket);
        return ResponseEntity.ok(estado);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
    }
}

    @Operation( summary = "Este endpoint permite crear un ticket" )
    @ApiResponses(value = { @ApiResponse(responseCode = "400",description = "BAD REQUEST: Indica que la peticion ha sido mal estructurada o por que falta(n) algun dato ",
    content = @Content(schema = @Schema(implementation = Ticket.class))),
    @ApiResponse(responseCode = "201",description = "CREATED: Indica ticket ha sido creado exitosamente",
    content = @Content(schema = @Schema(implementation = Ticket.class)))
    }
    )
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

                if(ticket.getHistorial() == null ||ticket.getHistorial().isEmpty()){
                    return ResponseEntity.badRequest().body("El ID del motivo es requerido");
                }

                ticketService.createTicket(ticket);
                return ResponseEntity.status(HttpStatus.CREATED).body("El ticket ha sido creado con exito");
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
            }
        }

        
        @Operation( summary = "Este endpoint permite listar todos los tickets" )
        @ApiResponses(value = { @ApiResponse(responseCode = "204",description = "NO CONTENT: Indica que no hay tickets creados ",
        content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "200",description = "OK: Indica ticket que existen tickets",
        content = @Content(schema = @Schema(implementation = Ticket.class)))
         }
         )
        @GetMapping("/listartks")
        public ResponseEntity<List<Ticket>> getAllTickets() {
            try {
                return ResponseEntity.ok(ticketService.getAllTicket());
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }

        @Operation( summary = "Este endpoint permite listar los tickets por su ID" )
        @ApiResponses(value = { @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica que el ticket no ha sido encontrado ",
        content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "200",description = "OK: Indica ticket que el ticket existe",
        content = @Content(schema = @Schema(implementation = Ticket.class)))
         }
         )
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

    



