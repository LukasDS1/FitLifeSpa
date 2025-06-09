package com.example.resena_service.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.resena_service.model.Resenia;
import com.example.resena_service.service.ReseniaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/resenias")
@RequiredArgsConstructor
public class ReseniaController {
    
    private final ReseniaService reseniaService;

    @GetMapping("/total")
    public ResponseEntity<List<Resenia>> findAllResenias(){
        List<Resenia> resenias = reseniaService.listar();
        if (resenias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            Resenia resenia = reseniaService.buscarId(id);
            return ResponseEntity.ok(resenia);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<?> addResenia(@RequestBody Resenia resenia){
        Resenia resenia1 = reseniaService.agregarResenia(resenia);
        if (resenia1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan campos o esta mal estructurado el body");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resenia1);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteResenia(Long id){
        Resenia resenias = reseniaService.buscarId(id);
        if (resenias == null) {
            return ResponseEntity.notFound().build();
        } else{
            reseniaService.Eliminar(id);
            return ResponseEntity.ok("El contenido se elimino correctamente");
        }

    }

}
