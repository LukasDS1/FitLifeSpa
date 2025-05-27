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
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api-v1/historial")
@RequiredArgsConstructor

public class HistorialController {

    private final HistorialService historialService;

    @GetMapping("/listarhistorial")
    public ResponseEntity<List<Historial>> getAllHistorial() {
        try {
            return ResponseEntity.ok(historialService.getHistorial());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/listarhistorial/{idHistorial}")
    public ResponseEntity<Historial> getIdHistorial(@PathVariable Long idHistorial) {
        try {
            return ResponseEntity.ok(historialService.getHistorialById(idHistorial));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    

    




    




    




}
