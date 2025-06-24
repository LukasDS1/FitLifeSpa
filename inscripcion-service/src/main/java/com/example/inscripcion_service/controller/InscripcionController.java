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
import com.example.inscripcion_service.model.Inscripcion;

import com.example.inscripcion_service.service.InscripcionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor
public class InscripcionController {
    
    private final InscripcionService inscriService;


    @Operation(summary = "Permite obtener una lista con todas las inscripciones")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con todas las reservas hechas", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "204", description = "no devolvera nada ya que la lista esta vacia.", content = @Content(schema = @Schema(implementation = Inscripcion.class)))
    } )
    @GetMapping("/inscripciones/total")
    public ResponseEntity<List<Inscripcion>> allInscripciones(){
        List<Inscripcion> lista = inscriService.listarInscripcion();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Permite obtener una lista de inscripciones hechas por un usuario mediante su id")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con todas las inscripciones hechas por un usuario", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "204", description = "no devolvera nada ya que la lista esta vacia.", content = @Content(schema = @Schema(implementation = Inscripcion.class)))
    } )
    @GetMapping("/inscripciones/usuario/{id}")
    public ResponseEntity<List<Inscripcion>> findByIdUser(@PathVariable Long id){
        List<Inscripcion> lista = inscriService.listarPorUsuario(id);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Permite obtener una lista de inscripciones con una clase en especifico")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con todas las inscripciones que tengan la clase buscada", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "204", description = "no devolvera nada ya que la lista esta vacia.", content = @Content(schema = @Schema(implementation = Inscripcion.class)))
    } )
    @GetMapping("/inscripciones/clase/{id}")
    public ResponseEntity<List<Inscripcion>> findByIdClass(@PathVariable Long id){
        List<Inscripcion> lista = inscriService.listarIncripcionesPorClase(id);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Permite obtener una lista de inscripciones mediante su estado")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con todas las inscripciones que tengan el estado buscado", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "204", description = "no devolvera nada ya que la lista esta vacia.", content = @Content(schema = @Schema(implementation = Inscripcion.class)))
    } )
    @GetMapping("/inscripciones/estado/{id}")
    public ResponseEntity<List<Inscripcion>> listByStatus(@PathVariable Long id){
        List<Inscripcion> lista = inscriService.listarIncripcionesPorEstado(id);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Permite hacer una inscripcion")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="201", description = "la inscripcion se registra correctamente y devolvera la misma inscripcion", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "400", description = "si no existe estado, clase, o hubo un error, no arrojara nada", content = @Content(schema = @Schema(implementation = Inscripcion.class)))
    } )
    @PostMapping("/inscripciones")
    public ResponseEntity<Inscripcion> addInscripcion(@RequestBody Inscripcion inst){
        Long estado = inst.getIdEstado();
        Long clase = inst.getIdClase();
        if(inscriService.validarEstado(estado) && inscriService.validarClase(clase)){
            try {
                Inscripcion inscripcion = inst;
                inscriService.saveInscripcionValidada(inscripcion);
                return ResponseEntity.status(HttpStatus.CREATED).body(inscripcion);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Permite obtener una inscripcion mediante su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genera una inscripcion con la ID buscada", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "404", description = "En caso de no existir, devolvera un mensaje", content = @Content(schema = @Schema(implementation = Inscripcion.class)))
    } )
    @GetMapping("/inscripciones/{id}") 
    public ResponseEntity<?> findInscById(@PathVariable Long id){
        if (inscriService.validacion(id) == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esta inscripcion no existe");
        } else {
            Inscripcion inscrip = inscriService.buscarporId(id);
            return ResponseEntity.ok(inscrip);
        } 
    }

    @Operation(summary = "Permite eliminar una inscripcion mediante su id")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="204", description = "eliminara la inscripcion y devolvera NoContent", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "404", description = "En caso de no existir devolvera un mensaje", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "400", description = "En caso de ocurrir un error, devolvera BadRequest", content = @Content(schema = @Schema(implementation = Inscripcion.class)))
    } )
    @DeleteMapping("/inscripciones/{id}")
    public ResponseEntity<?> deleteInscripcion(@PathVariable Long id){
        if(inscriService.validacion(id) == false){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La inscripcion no existe");
        }
        try {
            inscriService.eliminarInscripcion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
          
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Permite actualizar una inscripcion mediante su id")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Actualiza los datos de la inscripcion y devuelve la inscripcion nueva", content = @Content(schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "404", description = "En caso de error, devolvera 400, bad request", content = @Content(schema = @Schema(implementation = Inscripcion.class)))
    } )
    @PutMapping("/inscripciones/{id}")
    public ResponseEntity<Inscripcion> updateInscripcion (@PathVariable Long id, @RequestBody Inscripcion insc){
        try {
            Inscripcion inscrip1 = new Inscripcion();
            inscrip1.setIdInscripcion(id);
            inscrip1.setFechaInscripcion(insc.getFechaInscripcion());
            inscrip1.setIdClase(insc.getIdClase());
            inscrip1.setIdEstado(insc.getIdEstado());
            inscrip1.setIdUsuario(insc.getIdUsuario());
            inscriService.saveInscripcionValidada(inscrip1);
            return ResponseEntity.ok(inscrip1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
