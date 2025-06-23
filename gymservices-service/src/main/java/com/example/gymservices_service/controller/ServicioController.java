package com.example.gymservices_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gymservices_service.model.Servicio;
import com.example.gymservices_service.service.ServicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api-v1/service")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @Operation(summary = "Lista todos los servicios")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Servicios listados correctamente",
            content = @Content(schema = @Schema(implementation = Servicio.class))),
        @ApiResponse(
            responseCode = "204",
            description = "No hay servicios disponibles"
        )
    })
    @GetMapping("/listartodos")
    public ResponseEntity<List<Servicio>> allservices() {
        try {
            return ResponseEntity.ok(servicioService.allServices());
        } catch (Exception e) {       
        return ResponseEntity.noContent().build();
        }
    }
    
    @Operation(summary = "Obtiene un servicio por su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Servicio encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Servicio no encontrado",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(
            responseCode = "400", 
            description = "ID inválido",
            content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/exists/{idServicio}")
    public ResponseEntity<?> existsById(@PathVariable Long idServicio) {
        if (idServicio != null) {
            Servicio servicio = servicioService.serviceById(idServicio);
            if (servicio != null) {
                return ResponseEntity.ok(servicio);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servicio con id " + idServicio + " no encontrado.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar id de servicio: " + idServicio);
    }
    
    @Operation(summary = "Crea un nuevo servicio")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Servicio creado con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Error al añadir el servicio",
            content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/add")
    public ResponseEntity<?> createService(@RequestBody Servicio serviceObject) {
        if (serviceObject != null) {
            servicioService.addService(serviceObject);
            return ResponseEntity.status(HttpStatus.CREATED).body(serviceObject);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al añadir el servicio");
    }

    @Operation(summary = "Elimina un servicio por su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Servicio eliminado correctamente"),
        @ApiResponse(
            responseCode = "404", 
            description = "Servicio no encontrado",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(
            responseCode = "400", 
            description = "ID inválido")
    })
    @DeleteMapping("/delete/{idServicio}")
    public ResponseEntity<?> deleteById(@PathVariable Long idServicio) {
        if (idServicio != null) {
            if (servicioService.deleteService(idServicio)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Servicio eliminado correctamente.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar el servicio.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "Actualiza un servicio existente")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Servicio actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Servicio no encontrado",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos inválidos del servicio",
            content = @Content(mediaType = "text/plain"))
    })
    @PutMapping("update/{idServicio}")
    public ResponseEntity<?> putMethodName(@PathVariable Long idServicio, @RequestBody Servicio newServicio) {
        if (idServicio != null && newServicio.getNombre() != null && newServicio.getDescripcion() != null &&
        !newServicio.getNombre().isEmpty() && !newServicio.getDescripcion().isEmpty()) {
            Servicio servicio = servicioService.updateService(idServicio, newServicio);
            if (servicio != null) {
                return ResponseEntity.status(HttpStatus.OK).body(servicio);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servicio no encontrado.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id del servicio o atributos del servicio no pueden ser nulos.");
    }
    
}
