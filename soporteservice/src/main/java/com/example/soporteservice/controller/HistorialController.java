package com.example.soporteservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.soporteservice.model.Historial;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.service.HistorialService;
import com.example.soporteservice.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api-v1/historial")
@RequiredArgsConstructor

public class HistorialController {

    private final HistorialService historialService;
    private final TicketService ticketService;

    @Operation( summary = "Este endpoint permite listar los historiales asociados a un ticket " )
    @ApiResponses(value = { @ApiResponse(responseCode = "204",description = "NO CONTENT: Indica que no hay historiales asociados",
    content = @Content(schema = @Schema(implementation = Historial.class))),
    @ApiResponse(responseCode = "200",description = "OK: Indica que encontro los historiales asociados al id de ticket y las listas asociadas",
    content = @Content(schema = @Schema(implementation = Historial.class)))
    }
    )

    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<?> getHistorialesByTicketId(@PathVariable Long idTicket) {
    try {
        Optional<Ticket> exist = ticketService.getById(idTicket);

        if (exist.isPresent()) {
            List<Historial> historiales = historialService.getHistorialesByTicketId(idTicket);

            if (historiales.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(historiales);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket con ID: "+idTicket+" no encontrado");
    } catch (RuntimeException e) {
        throw new RuntimeException(e.getMessage());
    }
}

    @Operation( summary = "Este endpoint permite listar los historiales" )
    @ApiResponses(value = { @ApiResponse(responseCode = "204",description = "NO CONTENT: Indica quue no hay historiales ",
    content = @Content(schema = @Schema(implementation = Historial.class))),
    @ApiResponse(responseCode = "200",description = "OK: Indica que encontro todos los historiales",
    content = @Content(schema = @Schema(implementation = Historial.class)))
    }
    )
    @GetMapping("/listarhistorial")
    public ResponseEntity<List<Historial>> getAllHistorial() {
        try {
            return ResponseEntity.ok(historialService.getHistorial());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
     }


    @Operation( summary = "Este endpoint permite listar los historiales por ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica quue no ha encontrado el historial  ",
    content = @Content(schema = @Schema(implementation = Historial.class))),
    @ApiResponse(responseCode = "200",description = "OK: Indica que encontro todos el historial ",
    content = @Content(schema = @Schema(implementation = Historial.class)))
    }
    )
    @GetMapping("/listarhistorial/{idHistorial}")
    public ResponseEntity<Historial> getIdHistorial(@PathVariable Long idHistorial) {
        try {
            return ResponseEntity.ok(historialService.getHistorialById(idHistorial));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    

    




    




    




}
