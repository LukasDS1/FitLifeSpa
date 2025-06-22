package com.example.membresia_service.controller;

import java.util.List;
import java.util.Optional;

import javax.print.DocFlavor.STRING;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.service.PlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/plan")
@RequiredArgsConstructor

public class PlanController {

    private final PlanService planService;

    //Este endpoint permite crear planes
    @Operation( summary = "Este endpoint permite crear planes" )
    @ApiResponses(value = { @ApiResponse(responseCode = "201",description = "CREATED: Indica que el plan ha sido creado exitosamente",
    content = @Content(schema = @Schema(implementation = Plan.class))),
    @ApiResponse(responseCode = "400",description = "BAD REQUEST: Indica que la peticion ha sido mal estructurada",
    content = @Content(schema = @Schema(implementation = Plan.class)))
    }
    )
    @PostMapping("/crear")
    public ResponseEntity<Plan> savePlan(@RequestBody Plan plan) {
        try {
            planService.CreatePlan(plan);
            return ResponseEntity.status(HttpStatus.CREATED).body(plan);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    
    //Este endpoint permite listar todos los planes
    @Operation( summary = "Este endpoint permite listar todos los planes" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que los planes han sido listados de manera correcta",
    content = @Content(schema = @Schema(implementation = Plan.class))),
    @ApiResponse(responseCode = "204",description = "NO CONTENT: Indica que no hay planes registrados",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @GetMapping("/listall")
    public ResponseEntity<List<Plan>> getAllPlanes() {
        try {
            return ResponseEntity.ok(planService.getAllPlan());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }


    //Este endpoint permite listar los planes por ID
    @Operation( summary = "Este endpoint permite listar los planes por ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que el plan ha sido encontrado de manera correcta",
    content = @Content(schema = @Schema(implementation = Plan.class))),
    @ApiResponse(responseCode = "404",description = "Not found: Indica que el plan no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @GetMapping("/listid/{idPlan}")
    public ResponseEntity<?> getById(@PathVariable Long idPlan) {
        try {
            Optional<Plan> exist = planService.getbyID(idPlan);
            if (exist.isPresent()) {
                return ResponseEntity.ok(exist.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan con ID:"+idPlan+" no encontrada!");
        } catch (Exception e) {
            throw new RuntimeException("Membresia con ID: " +idPlan+ "no encontrada");
        }
    }

    //Este endpoint permite listar los planes asociados a una membresia 
    @Operation( summary = "Este endpoint permite listar los planes asociados a una membresia" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que la(s) membresias han sido encontradas de manera correcta",
    content = @Content(schema = @Schema(implementation = Plan.class))),
    @ApiResponse(responseCode = "404",description = "ACCEPTED: Indica que la peticion ha sido aceptada, ademas indica que el plan ha sido encontrado pero no hay membresias asociadas ",
    content = @Content(schema = @Schema(implementation = STRING.class))),
    @ApiResponse(responseCode = "202",description = "Not found: Indica que el plan no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = STRING.class)))
    }
    )
    @GetMapping("/listMembresiaplan/{idPlan}")
    public ResponseEntity<?> getMembresiasPorPlan(@PathVariable Long idPlan) {
        Optional<Plan> exist = planService.getbyID(idPlan);
        if(exist.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El plan con la ID: "+idPlan+ " no existe");
        }
        List<Membresia> membresias = planService.getMembresiasByPlanId(idPlan);
        if(membresias.isEmpty()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("El plan con la ID: "+idPlan+" no posee membresias asociadas");
        }
        return ResponseEntity.ok(membresias);
    }

    //Este endpoint permite eliminar planes por su ID
    @Operation( summary = "Este endpoint permite eliminar planes por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que el plan ha sido borrado de manera correcta",
    content = @Content(schema = @Schema(implementation = Plan.class))),
    @ApiResponse(responseCode = "404",description = "Not found: Indica que el plan no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = Plan.class)))
    }
    )
    @DeleteMapping("/deleteplan/{idPlan}")
    public ResponseEntity<String> deletePlan (@PathVariable Long idPlan) {
        try {
            planService.delete(idPlan);
            return ResponseEntity.status(HttpStatus.OK).body("Plan borrado con exito");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Este endpoint permite actualizar los planes por su ID
    @Operation( summary = "Este endpoint permite actualizar los planes por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que el plan ha sido actualizado de manera correcta",
    content = @Content(schema = @Schema(implementation = Plan.class))),
    @ApiResponse(responseCode = "404",description = "Not found: Indica que el plan no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @PatchMapping("/updateplan/{idPlan}")
    public ResponseEntity<?> updatePlan(@PathVariable Long idPlan,@RequestBody Plan plan) {
        try {
            plan.setIdPlan(idPlan);
            Plan plan2 = planService.updatePlan(plan);
            return ResponseEntity.ok().body(plan2);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El plan con ID: "+idPlan+"no ha sido encontrado");
        }
    }
    

}
