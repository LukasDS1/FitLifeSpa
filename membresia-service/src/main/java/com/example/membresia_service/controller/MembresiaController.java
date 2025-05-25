package com.example.membresia_service.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.service.MembresiaService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor
public class MembresiaController {  

    private final MembresiaService membresiaService;    

    @PostMapping("/crear")
    public ResponseEntity<Membresia> saveMembresia(@RequestBody Membresia membresia) {
        try {
            membresiaService.saveMembresia(membresia);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(membresia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Membresia>> getAllMembresias() {
        try {
            return ResponseEntity.ok(membresiaService.getByAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listarid")
    public ResponseEntity<Membresia> getById(@RequestBody Membresia membresia) {
        try {
            Optional<Membresia> exist = membresiaService.findByid(membresia.getIdMembresia());
            if (exist.isPresent()) {
                return ResponseEntity.ok(exist.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException("Membresia con ID: " + membresia.getIdMembresia() + "no encontrada");
        }
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<Membresia> deleteMembresias(@RequestBody Membresia membresia) {
        try {
            membresiaService.deleteMembresia(membresia.getIdMembresia());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Membresia> updateMembresia(@RequestBody Membresia membresia) {
        try {
            membresiaService.updatMembresia(membresia);
            return ResponseEntity.ok().body(membresia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }



}
