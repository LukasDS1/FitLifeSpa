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
import com.example.reserva_service.model.EstadoReserva;
import com.example.reserva_service.service.ReservaService;
import com.example.reserva_service.service.estadoReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    private final estadoReservaService estadoReservaService;

    @Operation(summary = "Permite obtener una lista con todas las Reservas")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con todas las reservas disponibles", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Reserva.class)))),
        @ApiResponse(responseCode = "204", description = "no devolvera nada ya que la lista esta vacia.")
    } )
    @GetMapping
    public ResponseEntity<List<Reserva>> findAll(){
        List<Reserva> lista = reservaService.listarReservas();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Permite obtener una Reserva mediante su ID unica")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genera una Reserva que fue buscada por su ID", content = @Content(schema = @Schema(implementation = Reserva.class))),
        @ApiResponse(responseCode = "404", description = "No devolvera nada ya que no encontro una Reserva con esa ID")
    } )
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> findByIdReserva(@PathVariable Long id){
        Reserva reserva = reservaService.buscarPorId(id);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reserva);
    }


    @Operation(summary = "Permite obtener una lista con las Reserva de un usuario mediante su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con todas las reservas disponibles mediante la ID del usuario", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Reserva.class)))),
        @ApiResponse(responseCode = "204", description = "no devolvera nada ya que la lista esta vacia.")
    } )
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Reserva>> findByidUser(@PathVariable Long id){
        List<Reserva> reservas = reservaService.listarPorIdUser(id);
        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Permite Agregar una reserva")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="201", description = "Agrega una reserva a la base de datos y devolvera el objeto Reserva", content = @Content(schema = @Schema(implementation = Reserva.class))),
        @ApiResponse(responseCode = "400", description = "En caso de un error o ya existir la reserva, devolvera BadRequest")
    } )
    @PostMapping("/add")
    public ResponseEntity<?> addReserva(@RequestBody Reserva reserva) {
        Reserva reserva1 = reservaService.agregarReserva(reserva);
        if (reserva1 == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva1);
    }

    @Operation(summary = "Permite actualizar una reserva existente con su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Actualiza los datos de la reserva y devuelve el objeto Reserva", content = @Content(schema = @Schema(implementation = Reserva.class))),
        @ApiResponse(responseCode = "400", description = "En caso de error arrojara Bad Request")
    } )
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
            reserva1.setEstadoReservas(reserva.getEstadoReservas());
            reservaService.agregarReserva(reserva1);

            return ResponseEntity.ok(reserva1);

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Permite eliminar una reserva existente mediante su id")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Se borra la reserva con su id y devuelve un mensaje confirmando la eliminacion"),
        @ApiResponse(responseCode = "404", description = "En caso de no existir, arrojara un mensaje especificandolo")
    } )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReserva (@PathVariable Long id){
        Reserva reserva1 = reservaService.buscarPorId(id);
        if (reserva1 != null) {
            reservaService.borrarReserva(id);
            return ResponseEntity.ok("Reserva eliminada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada");
        }
    }

    @Operation(summary = "Permite eliminar el estado de una reserva")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Se elimina el estado de la reserva"),
        @ApiResponse(responseCode = "404", description = "En caso de no existir, arrojara not found")
    } )
    @DeleteMapping("/estados/delete/{id}")
    public ResponseEntity<?> deleteEstadoReserva (@PathVariable Long id){
        EstadoReserva estado = estadoReservaService.listarPorId(id);
        if (estado == null) {
            return ResponseEntity.notFound().build();
        }
        estadoReservaService.eliminarEstadoReserva(id);
        return ResponseEntity.ok("Estado Eliminado");
    }

    @Operation(summary = "Permite objeter una lista de los estados de reserva")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genera una lista con todos los estados de reserva", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EstadoReserva.class)))),
        @ApiResponse(responseCode = "204", description = "en caso de no encontrar, arroja una lista vacia")
    } )
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoReserva>> listarTodos() {
        List<EstadoReserva> estados = estadoReservaService.listarTodo();
        if (estados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estados);
    }

    
    @Operation(summary = "Permite objeter un estado de reserva mediante su id")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Se genera un objeto de Estado Reserva", content = @Content(schema = @Schema(implementation = EstadoReserva.class))),
        @ApiResponse(responseCode = "204", description = "en caso de no encontrar, no devolvera nada")
    } )
    @GetMapping("/estados/{id}")
    public ResponseEntity<EstadoReserva> obtenerEstadoPorId(@PathVariable Long id) {
        try {
            EstadoReserva estado = estadoReservaService.listarPorId(id);
            return ResponseEntity.ok(estado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "agregar un estado de reserva nuevo")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="201", description = "Se agrega el objeto, y devolvera el estado de reserva", content = @Content(schema = @Schema(implementation = EstadoReserva.class))),
        @ApiResponse(responseCode = "400", description = "en caso de error, no agregara nada")
    } )
    @PostMapping("/estados")
    public ResponseEntity<EstadoReserva> agregarEstadoReserva(@RequestBody EstadoReserva estado) {
        EstadoReserva nuevoEstado = estadoReservaService.agregarEstadoReserva(estado);
        if (nuevoEstado != null) {
            return ResponseEntity.ok(nuevoEstado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
