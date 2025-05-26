package com.example.membresia_service.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.service.PlanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/plan")
@RequiredArgsConstructor

public class PlanController {

    private final PlanService planService;

    @PostMapping("/crear")
    public ResponseEntity<Plan> savePlan(@RequestBody Plan plan) {
        try {
            planService.CreatePlan(plan);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(plan);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


     @GetMapping("/listar")
    public ResponseEntity<List<Plan>> getAllPlanes() {
        try {
            return ResponseEntity.ok(planService.getAllPlan());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

     @GetMapping("/listarid")
    public ResponseEntity<Plan> getById(@RequestBody Plan plan) {
        try {
            Optional<Plan> exist = planService.getbyID(plan.getIdPlan());
            if (exist.isPresent()) {
                return ResponseEntity.ok(exist.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException("Membresia con ID: " + plan.getIdPlan() + "no encontrada");
        }
    }

      @DeleteMapping("/borrar")
    public ResponseEntity<String> deletePlan (@RequestBody Plan plan) {
        try {
            planService.delete(plan.getIdPlan());
            return ResponseEntity.ok().body("Plan borrado con exito!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Plan con ID: "+plan.getIdPlan()+" no encontrado");
        }
    }

     @PutMapping("/update")
    public ResponseEntity<Plan> updatePlan(@RequestBody Plan plan) {
        try {
            planService.updatePlan(plan);
            return ResponseEntity.ok().body(plan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    

}
