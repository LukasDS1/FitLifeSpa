package com.example.gymservices_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gymservices_service.model.Servicio;
import com.example.gymservices_service.service.ServicioService;
import lombok.RequiredArgsConstructor;
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
    
    @PostMapping("/add")
    public ResponseEntity<?> createService(@RequestBody Servicio serviceObject) {
        if (serviceObject != null) {
            servicioService.addService(serviceObject);
            return ResponseEntity.status(HttpStatus.CREATED).body(serviceObject);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al añadir el servicio");
    }

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
