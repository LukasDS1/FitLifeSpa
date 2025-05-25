package com.example.reserva_service.controller;

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

import com.example.reserva_service.model.Reserva;
import com.example.reserva_service.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<Reserva>> findAll(){
        List<Reserva> lista = reservaService.listarReservas();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> findByIdReserva(@PathVariable Long id){
        Reserva reserva = reservaService.buscarPorId(id);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Reserva>> findByidUser(@PathVariable Long id){
        List<Reserva> reservas = reservaService.listarPorIdUser(id);
        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservas);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReserva(@RequestBody Reserva reserva) {
        Reserva reserva1 = reservaService.agregarReserva(reserva);
        if (reserva1 == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva1);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Reserva> updateReserva(@RequestBody Reserva reserva, @PathVariable Long id){
        try {
            Reserva reserva1 = reservaService.buscarPorId(id);
            if (reserva1 == null) {
                return ResponseEntity.notFound().build();
            }
            reserva1.setDescripcion(reserva.getDescripcion());
            reserva1.setFechaContrato(reserva.getFechaContrato());
            reserva1.setFechaReserva(reserva.getFechaReserva());
            reserva1.setIdEntrenador(reserva.getIdEntrenador());
            reserva1.setIdServicio(reserva.getIdServicio());
            reserva1.setIdUsuario(reserva1.getIdUsuario());

            reservaService.agregarReserva(reserva1);

            return ResponseEntity.ok(reserva1);

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReserva (@PathVariable Long id){
        if (reservaService.validarService(id)) {
            reservaService.borrarReserva(id);
            return ResponseEntity.ok("Reserva eliminada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada");
        }
    }
}
