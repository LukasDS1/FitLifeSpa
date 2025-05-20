package com.example.clase_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clase_service.model.Clase;
import com.example.clase_service.service.ClaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor
public class ClaseController {

    private final ClaseService claseService;

    public ResponseEntity<List<Clase>> getAllClases() {
        try {
            return ResponseEntity.ok(claseService.getClases());
        } catch (Exception e) {
            throw new RuntimeException("Error al conseguir todas las clases.");
        }
    }

    public ResponseEntity<Clase> getClaseId(@RequestBody Clase idClase) {
        try {
            Optional<Clase> clase = claseService.getClaseById(idClase.getIdClase());
            if (clase.isPresent()) {
                return ResponseEntity.ok(clase.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException("Excepción al buscar clase por id");
        }
    }

    public ResponseEntity<Boolean> deleteClaseById(@RequestParam Long idClase) {
        Boolean delete = claseService.deleteClase(idClase);
        if (delete) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> updateClaseId(@RequestParam Long idClase,@RequestBody Clase clase) {
        if (idClase == null && clase == null) {
            return ResponseEntity.badRequest().body("Parámetros no pueden ser nulos.");
        }
        return ResponseEntity.ok(claseService.updateClase(idClase, clase));
    }
}
