package com.example.soporteservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.service.MotivoService;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor

public class MotivoController {

    private final MotivoService motivoService;

@PostMapping("/crearmotivo")
public ResponseEntity<Motivo> crearMotivo(@RequestBody Motivo motivo) {
    try {
        System.out.println("Datos recibidos: " + motivo);
        return ResponseEntity.ok(motivoService.createMotivo(motivo));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}


    @GetMapping("/obtener")
    public ResponseEntity<?> getMotivo(@RequestBody Motivo motivo) {
        try{
            Optional<Motivo> exist = motivoService.getMotivo(motivo.getIdMotivo());
            if(exist.isPresent()){
                return ResponseEntity.ok(exist.get());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido obtener Motivo con ID: "+motivo.getIdMotivo());
        } catch (Exception e){
            throw new RuntimeException("No se ha podido obtener Motivo con ID: "+ motivo.getIdMotivo());
        }
    }
    






    



}
