package com.example.clase_service.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.clase_service.model.Clase;
import com.example.clase_service.service.ClaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api-v1/clase")
@RequiredArgsConstructor
public class ClaseController {


    private final ClaseService claseService;

    @Operation(summary = "Lista todas las clases disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Clases listadas correctamente", 
            content = @Content(schema = @Schema(implementation = Clase.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Error al obtener las clases")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Clase>> getAllClass() {
        try {
            return ResponseEntity.ok(claseService.getAllClases());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Crea una nueva clase")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Clase creada correctamente", 
            content = @Content(schema = @Schema(type = "string", example = "clase creada correctamente."))),
        @ApiResponse(
            responseCode = "400", 
            description = "Error al crear la clase")
    })
    @PostMapping("/crear")
    public ResponseEntity<String> createClass(@RequestBody Clase clase) {
        try {
            claseService.saveClase(clase);
            return ResponseEntity.ok().body("clase creada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la clase.");
        }
    }

    
    @Operation(summary = "Busca una clase por su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Clase encontrada", 
            content = @Content(schema = @Schema(implementation = Clase.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Clase no encontrada"),
        @ApiResponse(
            responseCode = "400", 
            description = "Error al buscar la clase por ID")
    })
    @GetMapping("/listarid/{idClase}")
    public ResponseEntity<Clase> getClaseById(@PathVariable Long idClase) {
        try {
            Optional<Clase> exist = claseService.getClaseById(idClase);
            if (exist.isPresent()) {
                return ResponseEntity.ok(exist.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException("Excepcion al buscar clase por id");
        }
    }

    @Operation(summary = "Elimina una clase por su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Clase eliminada correctamente", 
            content = @Content(schema = @Schema(type = "string", example = "Clase con ID: 1 Ha sido borrada con exito"))),
        @ApiResponse(
            responseCode = "404", 
            description = "Clase no encontrada"),
        @ApiResponse(
            responseCode = "400", 
            description = "Error al eliminar clase")
    })
    @DeleteMapping("/borrar/{idClase}")
    public ResponseEntity<String> deleteClaseById(@PathVariable Long idClase) { 
        try {
            Optional<Clase> exist = claseService.getClaseById(idClase);
            if (exist.isPresent()) {
                claseService.deleteClase(idClase);
                return ResponseEntity.ok().body("Clase con ID: " + idClase + " Ha sido borrada con exito");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException("Excepcion al buscar clase por id");
        }
    }

    @Operation(summary = "Actualiza los datos de una clase existente")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Clase actualizada correctamente", 
            content = @Content(schema = @Schema(type = "string", example = "Clase con ID: 1 actualizada con exito"))),
        @ApiResponse(
            responseCode = "400", 
            description = "Parámetros inválidos"),
        @ApiResponse(
            responseCode = "404", 
            description = "Clase no encontrada")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateClass(@RequestBody Clase clase) {
        try {
            Optional<Clase> exist = claseService.getClaseById(clase.getIdClase());
            if (exist.isPresent()) {
            claseService.updateClase(clase.getIdClase(), clase);
            return ResponseEntity.ok().body("Clase con ID: " + clase.getIdClase() + " actualizada con exito");
            }
            return ResponseEntity.badRequest().body("Parametros no pueden ser nulos");
            
        } catch (Exception e) {
            throw new RuntimeException("Excepcion al actualizar clase por id");
        }

    }

    @Operation(summary = "Obtiene el servicio relacionado a una clase por su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Servicio relacionado obtenido correctamente"),
        @ApiResponse(
            responseCode = "404", 
            description = "Clase no encontrada o sin servicio relacionado")
    })
    @GetMapping("/servicio/{idClase}")
    public ResponseEntity<Map<String, Object>> getServicioDeClase(@PathVariable Long idClase) {
        Map<String, Object> servicio = claseService.obtenerServicioDeClase(idClase);
        return ResponseEntity.ok(servicio);
    }
}
