package com.example.inscripcion_service.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inscripcion_service.model.Clase;
import com.example.inscripcion_service.model.Estado;
import com.example.inscripcion_service.model.Inscripcion;
import com.example.inscripcion_service.service.ClaseService;
import com.example.inscripcion_service.service.EstadoService;
import com.example.inscripcion_service.service.InscripcionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InscripcionController {
    
    private final InscripcionService inscriService;
    
    private final EstadoService estadoService;
  
    private final ClaseService claseService;

    @GetMapping("/inscripciones/total")
    public ResponseEntity<List<Inscripcion>> allInscripciones(){
        List<Inscripcion> lista = inscriService.listarInscripcion();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/inscripciones")
    public ResponseEntity<Inscripcion> addInscripcion(@RequestBody Inscripcion inst){
        Estado estado = inst.getEstado();
        Clase clase = inst.getClase();
        if(estadoService.validarEstado(estado) && claseService.validarClase(clase)){
            try {
                Inscripcion inscripcion = inst;
                inscriService.agragarInscripcion(inscripcion);
                return ResponseEntity.status(HttpStatus.CREATED).body(inscripcion);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/inscripciones/{id}") 
    public ResponseEntity<?> findInscById(@PathVariable Long id){
        if (inscriService.validacion(id) == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esta inscripcion no existe");
        } else {
            Inscripcion inscrip = inscriService.buscarporId(id);
            return ResponseEntity.ok(inscrip);
        } 
    }

    @DeleteMapping("/inscripciones/{id}")
    public ResponseEntity<?> deleteInscripcion(@PathVariable Long id){
        if(inscriService.validacion(id) == false){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La inscripcion no existe");
        }
        try {
            inscriService.eliminarInscripcion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/inscripciones/{id}")
    public ResponseEntity<Inscripcion> updateInscripcion (@PathVariable Long id, @RequestBody Inscripcion insc){
        try {
            Inscripcion inscrip1 = new Inscripcion();
            inscrip1.setIdInscripcion(id);
            inscrip1.setFechaInscripcion(insc.getFechaInscripcion());
            inscrip1.setClase(insc.getClase());
            inscrip1.setEstado(insc.getEstado());
            inscriService.agragarInscripcion(inscrip1);
            return ResponseEntity.ok(inscrip1);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }
}
