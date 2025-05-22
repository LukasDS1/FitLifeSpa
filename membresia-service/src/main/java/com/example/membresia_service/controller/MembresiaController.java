package com.example.membresia_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.service.MembresiaService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Membresia> saveMembresia (@RequestBody Membresia membresia) {
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
    public ResponseEntity<Membresia> getById(Membresia membresia){
        try {
            membresiaService.getMembresiaById(membresia.getIdMembresia());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
          return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<Membresia> deleteMembresias(Membresia membresia) {
        try {
            membresiaService.deleteMembresia(membresia.getIdMembresia());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/update")
    public ResponseEntity<Membresia> updateMembresia (@RequestBody Membresia membresia) {
        try {
            membresiaService.updatMembresia(membresia);
            return ResponseEntity.ok().body(membresia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        
    }


}
