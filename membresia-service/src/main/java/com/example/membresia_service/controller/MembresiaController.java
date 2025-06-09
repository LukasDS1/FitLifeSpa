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

    @GetMapping("/listallmembresia")
    public ResponseEntity<List<Membresia>> getAllMembresias() {
        try {
            return ResponseEntity.ok(membresiaService.getByAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("plan/{idPlan}")
    public ResponseEntity<List<Membresia>> getMembresiasByPlan(@PathVariable Long idPlan) {
    List<Membresia> membresias = membresiaService.findMembresiasByPlanId(idPlan);
    return ResponseEntity.ok(membresias);
    }

   @GetMapping("usuario/{idUsuario}")
public ResponseEntity<?> getMembresiasPorUsuario(@PathVariable Long idUsuario) {
    boolean existe = membresiaService.validarUsuario(idUsuario);
    if (!existe) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario con ID " + idUsuario + " no existe.");
    }

    List<Membresia> membresias = membresiaService.findbyidUsuario(idUsuario);
    if (membresias.isEmpty()) {
        return ResponseEntity.ok("El usuario existe pero no tiene membresías asignadas.");
    }

    return ResponseEntity.ok(membresias);
}

    @GetMapping("/listid/{idMembresia}")
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

  @DeleteMapping("/deletemembresia/{idMembresia}")  
  public ResponseEntity<String> deleteMembresias(@PathVariable Long idMembresia) {
    try {
        boolean deleted = membresiaService.deleteMembresia(idMembresia);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Membresía borrada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Membresía con ID: " + idMembresia + " no encontrada");
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error técnico: " + e.getMessage());
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

    @PutMapping("/assignuser/{idUsuario}")
    public ResponseEntity<String> assignUser(@RequestBody Membresia membresia, @PathVariable Long idUsuario) {
    try {
        membresiaService.assignUsuarioToMembership(membresia.getIdMembresia(), idUsuario);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Usuario con ID: " + idUsuario + " agregado con éxito a la membresía ID: " + membresia.getIdMembresia());
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
    }

}
