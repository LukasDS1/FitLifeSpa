package com.example.membresia_service.controller;

import java.util.List;
import java.util.Optional;

import javax.print.DocFlavor.STRING;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
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
    public ResponseEntity<?> savePlan(@RequestBody Plan plan) {
    try {
        Plan creado = planService.CreatePlan(plan);

        EntityModel<Plan> model = EntityModel.of(creado);
        model.add(linkTo(methodOn(PlanController.class).getById(creado.getIdPlan())).withSelfRel());
        model.add(linkTo(methodOn(PlanController.class).getAllPlanes()).withRel("listar_todos"));
        model.add(linkTo(methodOn(PlanController.class).getMembresiasPorPlan(creado.getIdPlan())).withRel("ver_membresias"));

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("Error al crear el plan");
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
    public ResponseEntity<?> getAllPlanes() {
    List<Plan> planes = planService.getAllPlan();
    if (planes.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay planes registrados");
    }

    List<EntityModel<Plan>> planesModel = planes.stream().map(plan -> {
        EntityModel<Plan> model = EntityModel.of(plan);
        model.add(linkTo(methodOn(PlanController.class).getById(plan.getIdPlan())).withSelfRel());
        model.add(linkTo(methodOn(PlanController.class).getMembresiasPorPlan(plan.getIdPlan())).withRel("ver_membresias"));
        return model;
    }).toList();

    CollectionModel<EntityModel<Plan>> collection = CollectionModel.of(
        planesModel,
        linkTo(methodOn(PlanController.class).getAllPlanes()).withSelfRel()
    );

    return ResponseEntity.ok(collection);
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
            Plan plan = exist.get();
            EntityModel<Plan> model = EntityModel.of(plan);
            model.add(linkTo(methodOn(PlanController.class).getById(idPlan)).withSelfRel());
            model.add(linkTo(methodOn(PlanController.class).getAllPlanes()).withRel("listar_todos"));
            model.add(linkTo(methodOn(PlanController.class).getMembresiasPorPlan(idPlan)).withRel("ver_membresias"));
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan con ID: " + idPlan + " no encontrado");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el plan");
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
    if (exist.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El plan con ID: " + idPlan + " no existe");
    }

    List<Membresia> membresias = planService.getMembresiasByPlanId(idPlan);
    if (membresias.isEmpty()) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("El plan con ID: " + idPlan + " no tiene membresías asociadas");
    }

    CollectionModel<Membresia> model = CollectionModel.of(membresias);
    model.add(linkTo(methodOn(PlanController.class).getById(idPlan)).withRel("detalle_plan"));
    model.add(linkTo(methodOn(PlanController.class).getAllPlanes()).withRel("todos_los_planes"));

    return ResponseEntity.ok(model);
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
    public ResponseEntity<?> deletePlan(@PathVariable Long idPlan) {
    try {
        planService.delete(idPlan);
        EntityModel<String> model = EntityModel.of("Plan eliminado correctamente");
        model.add(linkTo(methodOn(PlanController.class).getAllPlanes()).withRel("listar_todos"));
        return ResponseEntity.ok(model);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
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
    public ResponseEntity<?> updatePlan(@PathVariable Long idPlan, @RequestBody Plan plan) {
    try {
        plan.setIdPlan(idPlan);
        Plan actualizado = planService.updatePlan(plan);

        EntityModel<Plan> model = EntityModel.of(actualizado);
        model.add(linkTo(methodOn(PlanController.class).getById(idPlan)).withSelfRel());
        model.add(linkTo(methodOn(PlanController.class).getMembresiasPorPlan(idPlan)).withRel("ver_membresias"));
        return ResponseEntity.ok(model);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El plan con ID: " + idPlan + " no fue encontrado");
    }
}
    

}
