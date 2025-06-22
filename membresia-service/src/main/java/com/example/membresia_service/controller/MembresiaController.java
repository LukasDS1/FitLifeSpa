package com.example.membresia_service.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.repository.PlanRepository;
import com.example.membresia_service.service.MembresiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api-v1/membresia")
@RequiredArgsConstructor
public class MembresiaController {  

    private final MembresiaService membresiaService;   

    private final PlanRepository planRepository;

    //crear una membresia
    @Operation( summary = "Este endpoint permite crear una membresia" )
    @ApiResponses(value = { @ApiResponse(responseCode = "201",description = "CREATED: Indica que la membresia ha sido creada",
    content = @Content(schema = @Schema(implementation = Membresia.class))),
    @ApiResponse(responseCode = "400",description = "BAD REQUEST: Indica que los campos para crar las membresias estan mal estructurados",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @PostMapping("/crearmembresia")
    public ResponseEntity<String> saveMembresia(@RequestBody Membresia membresia) {
        try {
            membresiaService.saveMembresia(membresia);
            return ResponseEntity.status(HttpStatus.CREATED).body("Membresia creada con exito!");
        } catch (IllegalArgumentException  e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error "+e.getMessage());
        } catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error inesperado: " + e.getMessage());
    }
    }

     //listar todas las membresias
    @Operation( summary = "Este endpoint permite listar todas membresia" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que las membresia han sido encontradas",
    content = @Content(schema = @Schema(implementation = Membresia.class))),
    @ApiResponse(responseCode = "204",description = "NO CONTENT: Indica que no se ha encontrado contenido",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @GetMapping("/listallmembresia")
    public ResponseEntity<List<Membresia>> getAllMembresias() {
        try {
            return ResponseEntity.ok(membresiaService.getByAll());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    //listar todas las membresias por id con el plan al cual estan asociadas
    @Operation(summary = "Este endpoint permite listar todas las membresías asociadas a un plan")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "OK: Indica que las membresías han sido encontradas",
    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Membresia.class)))),
    @ApiResponse(responseCode = "202", description = "ACCEPTED: El plan existe pero no tiene membresías asignadas",
    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
    @ApiResponse(responseCode = "404", description = "NOT FOUND: El plan con el ID proporcionado no existe",
    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
    })
    @GetMapping("/plan/{idPlan}")
    public ResponseEntity<?> getMembresiasByPlan(@PathVariable Long idPlan) {
    Optional<Plan> planOptional = planRepository.findById(idPlan);
    if (!planOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El plan con ID " + idPlan + " no existe.");
    }
    List<Membresia> membresias = membresiaService.findMembresiasByPlanId(idPlan);
    if(membresias.isEmpty()){
        return ResponseEntity.accepted().body("El plan existe pero no tiene membresias asignadas");
    }
    return ResponseEntity.ok(membresias);
    }


    //listar todas los usuarios por id asociados con membresias
    @Operation( summary = "Este endpoint permite listar todos los usuarios asociados a una membresia" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que los usuarios han sido encontrados con sus membresias",
    content = @Content(schema = @Schema(implementation = Membresia.class))),
    @ApiResponse(responseCode = "404",description = "NO CONTENT: Indica que el usuario no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "200",description = "OK: Indica que los usuarios existen pero no tienen membresia asociadas",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
   @GetMapping("/usuario/{idUsuario}")
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

    //listar las membresias por id
    @Operation( summary = "Este endpoint permite listar  las membresias por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que la membresia ha sido encontrada",
    content = @Content(schema = @Schema(implementation = Membresia.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica que no se ha encontrado la membresia",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @GetMapping("/listid/{idMembresia}")
    public ResponseEntity<?> getById(@PathVariable Long idMembresia) {
        try {
            Optional<Membresia> exist = membresiaService.findByid(idMembresia);
            if (exist.isPresent()) {
                return ResponseEntity.ok(exist.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Membresia con ID: " + idMembresia + "no encontrada");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //borrar las membresias por id
    @Operation( summary = "Este endpoint permite borrar las membresias por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "202",description = "ACEPTED: Indica que la membresia ha sido encontrada y borrada",
    content = @Content(schema = @Schema(implementation = Membresia.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica que la membresia no ha sido encontrada",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR: Indica que ha fallado de manera interna el servicio",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
  @DeleteMapping("/deletemembresia/{idMembresia}")  
  public ResponseEntity<String> deleteMembresias(@PathVariable Long idMembresia) {
    try {
        boolean deleted = membresiaService.deleteMembresia(idMembresia);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Membresía borrada con exito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Membresía con ID: " + idMembresia + " no encontrada");
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error técnico: " + e.getMessage());
    }
}   

     //actualizar las membresias por id
    @Operation( summary = "Este endpoint permite actualizar las membresias por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que la membresia ha sido encontrada y actualizada",
    content = @Content(schema = @Schema(implementation = Membresia.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica que la membresia no ha sido encontrada",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @PatchMapping("/update/{idMembresia}")
    public ResponseEntity<?> updateMembresia(@PathVariable Long idMembresia, @RequestBody Membresia membresia){
        try {
            membresia.setIdMembresia(idMembresia);
            Membresia updateMembresia = membresiaService.UpdateUserById(membresia);
            return ResponseEntity.ok(updateMembresia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

     //setea el plan a una membresia
    @Operation( summary = "Este endpoint permite setear un plan a las membresias" )
    @ApiResponses(value = { @ApiResponse(responseCode = "400",description = "BAD REQUEST: Indica que la request esta mal estructurada debido a que falta el ID del plan",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "202",description = "ACEPTED: Indica que el plan ha sido seteado de manera correcta",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "400",description = "BAD REQUEST: Indica que la request esta mal estructurada",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
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

    //assigna un usuario a una membresia
    @Operation( summary = "Este endpoint permite actualizar las membresias por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "202",description = "ACEPTED: Indica que el usuario ha sido asignado de manera correcta",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "400",description = "BAD REQUEST: Indica que la request esta mal estructurada",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
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


    //elimina un usuario de una membresia
    @Operation( summary = "Este endpoint permite eliminar un usuario de una membresia" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que el usuario ha sido eliminado de la membresia de manera correcta",
    content = @Content(schema = @Schema(implementation = Membresia.class))),
    @ApiResponse(responseCode = "202",description = "ACCEPTED: Indica que la peticion ha sido aceptada pero no procesada debido a que lanzara un error impreso en la consola Ej: que el usuario el cual quiere borrar no exista ",
    content = @Content(schema = @Schema(implementation = Membresia.class)))
    }
    )
    @DeleteMapping("/deleteuser/{idUser}")
    public ResponseEntity<String> deleteUser(@PathVariable Long idUser,@RequestBody Membresia membresia){
        try {
            membresiaService.deleteUsersFromMembresia(membresia.getIdMembresia(), idUser);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario borrado correctamente de la membresia");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }


}
