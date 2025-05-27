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
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api-v1/membresia")
@RequiredArgsConstructor
public class MembresiaController {  

    private final MembresiaService membresiaService;    

    @PostMapping("/crearmembresia")
    public ResponseEntity<String> saveMembresia(@RequestBody Membresia membresia) {
        try {
            membresiaService.saveMembresia(membresia);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Membresia creada con exito!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listarmembresia")
    public ResponseEntity<List<Membresia>> getAllMembresias() {
        try {
            return ResponseEntity.ok(membresiaService.getByAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listarid/{idMembresia}")
    public ResponseEntity<Membresia> getById(@PathVariable Long idMembresia) {
        try {
            Optional<Membresia> exist = membresiaService.findByid(idMembresia);
            if (exist.isPresent()) {
                return ResponseEntity.ok(exist.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException("Membresia con ID: " + idMembresia + "no encontrada");
        }
    }

    @DeleteMapping("/borrar/{idMembresia}")
    public ResponseEntity<String> deleteMembresias(@PathVariable Long idMembresia) {
        try {
            membresiaService.deleteMembresia(idMembresia);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Membresia borrada con exito!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Membresia con ID: "+idMembresia+" no encontrada");
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

    @PutMapping("/assignplan")
    public ResponseEntity<String> assignPlan(@RequestBody Membresia membresia) {
        try {
            if (membresia.getPlan() == null || membresia.getPlan().getIdPlan() == null) {
            return ResponseEntity.badRequest().body("Falta el ID del plan.");
        }   
            membresiaService.assignPlanToMembership(membresia.getIdMembresia(), membresia.getPlan().getIdPlan());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Plan con ID: "+membresia.getPlan().getIdPlan()+" agregada con exito a la membresia de ID: "+membresia.getIdMembresia());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        }
    }

    @PutMapping("/assignuser")
    public ResponseEntity<String> assignUser(@RequestBody Membresia membresia) {
        try {
            if(membresia.getUsuario().getEmail() == null){
                return ResponseEntity.badRequest().body("Falta el email del usuario.");
            }
            membresiaService.assignUsuarioToMembership(membresia.getIdMembresia(),membresia.getUsuario().getEmail());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario con Email"+membresia.getUsuario().getEmail()+" agregado con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
