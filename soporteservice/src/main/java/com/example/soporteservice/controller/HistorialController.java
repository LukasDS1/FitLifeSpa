package com.example.soporteservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.soporteservice.model.Historial;
import com.example.soporteservice.service.HistorialService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor

public class HistorialController {

    private final HistorialService historialService;

    @GetMapping("/listar")
    public ResponseEntity<List<Historial>> getAllHistorial() {
        try {
            return ResponseEntity.ok(historialService.getHistorial());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/listarid")
    public ResponseEntity<Historial> getIdHistorial(@RequestBody Historial historial) {
        try {
            return ResponseEntity.ok(historialService.getHistorialById(historial.getIdHistorial()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/crearhistorial")
    public ResponseEntity<Historial> createHistorial (@RequestBody Historial historial) {
        try {
            return ResponseEntity.ok(historialService.createHistorial(historial));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    




    




    




}
