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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/resenias")
@RequiredArgsConstructor
public class ReseniaController {
    
    private final ReseniaService reseniaService;

    @Operation(summary = "Permite obtener una lista con todas las Reservas")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con todas las resenias hechas", content = @Content(schema = @Schema(implementation = Resenia.class))),
        @ApiResponse(responseCode = "204", description = "no devolvera nada ya que la lista esta vacia.", content = @Content(schema = @Schema(implementation = Resenia.class)))
    } )
    @GetMapping("/total")
    public ResponseEntity<List<Resenia>> findAllResenias(){
        List<Resenia> resenias = reseniaService.listar();
        if (resenias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenias);
    }

    @Operation(summary = "Permite obtener una resenia mediante su id")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genera una resenia hecha con su id", content = @Content(schema = @Schema(implementation = Resenia.class))),
        @ApiResponse(responseCode = "404", description = "En caso de no encontrarla, no arrojara nada.", content = @Content(schema = @Schema(implementation = Resenia.class)))
    } )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            Resenia resenia = reseniaService.buscarId(id);
            return ResponseEntity.ok(resenia);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Permite obtener una lista con las resenias de un servicio")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con las resenias del servicio mediante su id", content = @Content(schema = @Schema(implementation = Resenia.class))),
        @ApiResponse(responseCode = "204", description = "Si la lista esta vacia no devolvera nada", content = @Content(schema = @Schema(implementation = Resenia.class)))
    } )
    @GetMapping("/servicio/{id}")
    public ResponseEntity<List<Resenia>> findReseniaByIdServicio(@PathVariable Long id){
        List<Resenia> lista = reseniaService.buscarPorIdServicio(id);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    
    @Operation(summary = "Permite agregar una resenia")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="201", description = "Se agrega la resenia y devuelve la misma resenia", content = @Content(schema = @Schema(implementation = Resenia.class))),
        @ApiResponse(responseCode = "400", description = "En caso de error devolvera un mensaje", content = @Content(schema = @Schema(implementation = Resenia.class)))
    } )
    @PostMapping
    public ResponseEntity<?> addResenia(@RequestBody Resenia resenia){
        Resenia resenia1 = reseniaService.agregarResenia(resenia);
        if (resenia1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan campos o esta mal estructurado el body");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resenia1);
    }


    @Operation(summary = "Permite eliminar una resenia")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Se elimina la resenia y devuelve un mensaje", content = @Content(schema = @Schema(implementation = Resenia.class))),
        @ApiResponse(responseCode = "404", description = "no devolvera nada ya que la lista esta vacia.", content = @Content(schema = @Schema(implementation = Resenia.class)))
    } )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteResenia(@PathVariable Long id){
        Resenia resenias = reseniaService.buscarId(id);
        if (resenias == null) {
            return ResponseEntity.notFound().build();
        } else{
            reseniaService.Eliminar(id);
            return ResponseEntity.ok("El contenido se elimino correctamente");
        }

    }

}
