package com.example.soporteservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.service.MotivoService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api-v1/motivo")
@RequiredArgsConstructor

public class MotivoController {

    private final MotivoService motivoService;



    @Operation( summary = "Este endpoint permite crear un motivo" )
    @ApiResponses(value = { @ApiResponse(responseCode = "400",description = "BAD REQUEST: Indica que la peticion no esta bien estructurada",
    content = @Content(schema = @Schema(implementation = Motivo.class))),
    @ApiResponse(responseCode = "201",description = "CREATED: Indica que el motivo ha sido creado con exito ",
    content = @Content(schema = @Schema(implementation = Motivo.class)))
    }
    )
    @PostMapping("/crearmotivo")
    public ResponseEntity<?> crearMotivo(@RequestBody Motivo motivo) {
    try {
        Motivo nuevoMotivo = motivoService.createMotivo(motivo);

        EntityModel<Motivo> motivoModel = EntityModel.of(nuevoMotivo);
        motivoModel.add(linkTo(methodOn(MotivoController.class).getMotivo(nuevoMotivo.getIdMotivo())).withRel("ver"));
        motivoModel.add(linkTo(methodOn(MotivoController.class).getAllMotivo()).withRel("todos"));
        return ResponseEntity.status(HttpStatus.CREATED).body(motivoModel);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
    @Operation( summary = "Este endpoint permite listar un motivo por la ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica que el motivo no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = Motivo.class))),
    @ApiResponse(responseCode = "200",description = "OK: Indica que el motivo ha sido encontrado con exito ",
    content = @Content(schema = @Schema(implementation = Motivo.class)))
    }
    )
    @GetMapping("/listarmotivo/{idMotivo}")
    public ResponseEntity<?> getMotivo(@PathVariable Long idMotivo) {
    try {
        Optional<Motivo> exist = motivoService.getMotivo(idMotivo);
        if (exist.isPresent()) {
            Motivo motivo = exist.get();
            EntityModel<Motivo> motivoModel = EntityModel.of(motivo);

            motivoModel.add(linkTo(methodOn(MotivoController.class).getMotivo(idMotivo)).withSelfRel());
            motivoModel.add(linkTo(methodOn(MotivoController.class).getAllMotivo()).withRel("todos"));
            return ResponseEntity.ok(motivoModel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se ha podido obtener Motivo con ID: " + idMotivo);
    } catch (Exception e) {
        throw new RuntimeException("No se ha podido obtener Motivo con ID: " + idMotivo);
    }
}
    @Operation( summary = "Este endpoint permite listar todos los motivos" )
    @ApiResponses(value = { @ApiResponse(responseCode = "204",description = "NO CONTENT: Indica que no hay motivos existentes",
    content = @Content(schema = @Schema(implementation = Motivo.class))),
    @ApiResponse(responseCode = "200",description = "OK: Indica que los motivos han sido listados exitosamete",
    content = @Content(schema = @Schema(implementation = Motivo.class)))
    }
    )
    @GetMapping("/listarmotivo")
    public ResponseEntity<?> getAllMotivo() {
    try {
        List<Motivo> motivos = motivoService.getAllMotivo();

        if (motivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Motivo>> motivosModel = motivos.stream().map(motivo -> {
            EntityModel<Motivo> model = EntityModel.of(motivo);
            model.add(linkTo(methodOn(MotivoController.class).getMotivo(motivo.getIdMotivo())).withRel("ver"));
            return model;
        }).toList();

        CollectionModel<EntityModel<Motivo>> collectionModel = CollectionModel.of(
            motivosModel,
            linkTo(methodOn(MotivoController.class).getAllMotivo()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay motivos registrados");
    }
}
    


   
    






    



}
