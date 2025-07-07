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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
            List<EntityModel<Historial>> historialModels = historiales.stream().map(historial -> {
                EntityModel<Historial> model = EntityModel.of(historial);

                model.add(linkTo(methodOn(HistorialController.class)
                    .getIdHistorial(historial.getIdHistorial())).withSelfRel());

                model.add(linkTo(methodOn(HistorialController.class)
                    .getAllHistorial()).withRel("todos"));

                model.add(linkTo(methodOn(HistorialController.class)
                    .getHistorialesByTicketId(idTicket)).withRel("por-ticket"));

                return model;
            }).toList();

            CollectionModel<EntityModel<Historial>> collectionModel = CollectionModel.of(
                historialModels,
                linkTo(methodOn(HistorialController.class)
                    .getHistorialesByTicketId(idTicket)).withSelfRel()
            );

            return ResponseEntity.ok(collectionModel);
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
public ResponseEntity<?> getAllHistorial() {
    try {
        List<Historial> historiales = historialService.getHistorial();

        if (historiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Historial>> historialModels = historiales.stream().map(historial -> {EntityModel<Historial> model = EntityModel.of(historial);
            model.add(linkTo(methodOn(HistorialController.class)
                .getIdHistorial(historial.getIdHistorial())).withRel("ver"));
            model.add(linkTo(methodOn(HistorialController.class)
                .getAllHistorial()).withSelfRel());
            return model;
        }).toList();

        CollectionModel<EntityModel<Historial>> collectionModel = CollectionModel.of(
            historialModels,
            linkTo(methodOn(HistorialController.class).getAllHistorial()).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
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
    public ResponseEntity<?> getIdHistorial(@PathVariable Long idHistorial) {
        try {
            Historial historial = historialService.getHistorialById(idHistorial);
            EntityModel<Historial> model = EntityModel.of(historial);
            model.add(linkTo(methodOn(HistorialController.class).getIdHistorial(idHistorial)).withSelfRel());
            model.add(linkTo(methodOn(HistorialController.class).getAllHistorial()).withRel("todos"));
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    

    




    




    




}
