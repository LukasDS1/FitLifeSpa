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

    @GetMapping("/listar")
    public ResponseEntity<List<Clase>> getAllClass() {
        try {
            return ResponseEntity.ok(claseService.getAllClases());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<String> createClass(@RequestBody Clase clase) {
        try {
            claseService.saveClase(clase);
            return ResponseEntity.ok().body("clase creada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la clase.");
        }
    }

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

    @GetMapping("/servicio/{idClase}")
    public ResponseEntity<Map<String, Object>> getServicioDeClase(@PathVariable Long idClase) {
        Map<String, Object> servicio = claseService.obtenerServicioDeClase(idClase);
        return ResponseEntity.ok(servicio);
    }
    
    


}
